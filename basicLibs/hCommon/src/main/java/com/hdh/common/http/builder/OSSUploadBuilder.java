package com.hdh.common.http.builder;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseIntArray;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.utils.IOUtils;
import com.alibaba.sdk.android.oss.model.CompleteMultipartUploadRequest;
import com.alibaba.sdk.android.oss.model.CompleteMultipartUploadResult;
import com.alibaba.sdk.android.oss.model.InitiateMultipartUploadRequest;
import com.alibaba.sdk.android.oss.model.InitiateMultipartUploadResult;
import com.alibaba.sdk.android.oss.model.PartETag;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.alibaba.sdk.android.oss.model.UploadPartRequest;
import com.alibaba.sdk.android.oss.model.UploadPartResult;
import com.google.gson.reflect.TypeToken;
import com.hdh.common.http.HTTP;
import com.hdh.common.http.handler.HttpConfigController;
import com.hdh.common.http.handler.ProgressCallback;
import com.hdh.common.http.pojo.FileResponseProvider;
import com.hdh.common.http.pojo.FileUploadProvider;
import com.hdh.common.http.pojo.OSSResult;
import com.hdh.common.http.pojo.OssRequestDataProvider;
import com.hdh.common.http.util.HttpConstants;
import com.hdh.common.http.util.UploadException;
import com.hdh.common.util.FileUtil;
import com.hdh.common.util.HSON;
import com.hdh.common.util.LogUtil;
import com.hdh.common.util.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

import static com.hdh.common.util.FileUtil.TEMP_IMAGE_DIR;


/**
 * 文件上传进度/分片上传
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/21 20:21
 */
public class OSSUploadBuilder {
    private static final String LOG_TAG = "OSSUploadBuilder";

    public static final String APP_ID = "appId";
    public static final String FILE_TYPE = "fileType";
    public static final String BUSINESS_ID = "businessId";
    public static final String FILE_NAME = "fileName";
    private File tempFileDir;

    List<File> files;
    int businessId = HttpConstants.BusinessIdUser;
    int type = HttpConstants.FileTypeImage;
    private ClientConfiguration mClientConfiguration;
    private int maxRetry = 2; //最大重试次数 默认两次
    private Context appContext;
    private SparseIntArray requestRetryFlags; //这里用来标记请求是否超过了重试次数
    private boolean compress; //是否做压缩处理
    private int reqW = 720; //压缩后的宽高  default
    private int reqH = 1080;//压缩后的宽高  default
    private int maxSize = 1 * 1024; //压缩后的质量大小,两百kb default
    private boolean multipart;
    private ProgressCallback mProgressCallback;


    public OSSUploadBuilder(Context appContext) {
        if (appContext != null) this.appContext = appContext.getApplicationContext();
        initOSSCfgs();
        tempFileDir = new File(Environment.getExternalStorageDirectory(), TEMP_IMAGE_DIR);
    }

    private void initOSSCfgs() {
        if (mClientConfiguration == null) {
            mClientConfiguration = new ClientConfiguration();
            mClientConfiguration.setConnectionTimeout(60 * 1000); // 连接超时，默认15秒
            mClientConfiguration.setSocketTimeout(60 * 1000); // socket超时，默认15秒
            mClientConfiguration.setMaxConcurrentRequest(Runtime.getRuntime().availableProcessors() * 2); // 最大并发请求数，默认5个
        }
    }

    /**
     * 单个文件
     *
     * @param file {@link File}
     * @return
     */
    public OSSUploadBuilder put(File file) {
        if (!checkCollection(file))
            files.add(file);
        return this;
    }

    public OSSUploadBuilder putAll(List<File> files) {
        if (files == null) return this;
        for (File file : files) {
            put(file);
        }
        return this;
    }

    public OSSUploadBuilder progress(ProgressCallback callback) {
        this.mProgressCallback = callback;
        return this;
    }

    public OSSUploadBuilder multiPart() {
        this.multipart = true;
        return this;
    }

    /**
     * 业务区分 默认 {@link HttpConstants#BusinessIdUser}
     *
     * @return {@link OSSUploadBuilder}
     */
    public OSSUploadBuilder businessId(int id) {
        this.businessId = id;
        return this;
    }

    /**
     * 上传类型 暂时只支持图片上传(默认)
     *
     * @param type {@link HttpConstants#FileTypeImage}
     * @return {@link OSSUploadBuilder}
     */
    public OSSUploadBuilder type(int type) {
        this.type = type;
        return this;
    }

    /**
     * 失败后最大重试次数，默认2次
     *
     * @param count 重试次数
     * @return
     */
    public OSSUploadBuilder maxRetry(int count) {
        this.maxRetry = count;
        mClientConfiguration.setMaxErrorRetry(maxRetry);
        return this;
    }

    /**
     * 必须要有文件存储权限
     *
     * @return
     */
//    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public OSSUploadBuilder compress() {
        this.compress = true;
        return this;
    }

    //    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public OSSUploadBuilder compress(boolean compress) {
        this.compress = compress;
        return this;
    }

    //    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public OSSUploadBuilder compress(boolean compress, int reqW, int reqH, int maxSize) {
        this.compress = compress;
        this.reqW = reqW;
        this.reqH = reqH;
        this.maxSize = maxSize;
        return this;
    }

    private boolean checkCollection(File file) {
        if (file == null || !file.exists()) {
            try {
                if (HttpConstants.DEBUG)
                    Log.d(HttpConstants.TAG, "图片不存在:" + file.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        if (files == null) {
            files = new ArrayList<>();
            return false;
        }
        return files.contains(file);
    }

    /**
     * 同步执行上传操作(必须子线程中调用)
     *
     * @return 返回图片在服务器总的路径, 可能为空
     */
    public List<String> execute() throws UploadException {
        if (files == null || files.size() == 0) return null;
        HttpConfigController mHandler = HTTP.getController();
        if (mHandler != null && mHandler.networkCheck()) { //前置检查
            return null;
        }
        final List<String> fileServerPaths = new ArrayList<>(files.size());
        requestRetryFlags = new SparseIntArray(files.size());
        //對文件進行前置處理。
        preHandleForAllFile();

        for (int i = 0; i < files.size(); i++) {
            final File file = files.get(i);
            requestRetryFlags.put(i, 0);
            uploadFile(i, fileServerPaths, file);
        }
        if (HttpConstants.DEBUG) Log.d(HttpConstants.TAG, "上传成功");
        //文件上传成功之后,把缓存目录删除掉
        FileUtil.deleteDir(tempFileDir);
        return fileServerPaths;

    }

    private void preHandleForAllFile() throws UploadException {
        //對文件進行一些前置處理
        for (File file :
                files) {
            file = handleCompressFile(file);
            totalFileSize += file.length();
        }
    }

    private void uploadFile(int index, List<String> fileServerPaths, File file) throws UploadException {
        file = handleCompressFile(file); //执行文件压缩处理
        try {
            OSSResult<FileUploadProvider> result = requestOSSToken(file);
            if (result != null && result.isSuccess()) {
                FileUploadProvider data = result.data;
                //拼装请求前置参数
                OSS ossClient = getOSSClient(data);
                if (multipart) {
                    CompleteMultipartUploadResult cmuResult = multipartUploadFile(file, data, ossClient);
                    parseCMUResult(index, fileServerPaths, file, cmuResult);
                } else {
                    PutObjectResult ossResult = requestOSSServer(file, data, ossClient);
                    parseOSSResult(index, fileServerPaths, file, ossResult);
                }
            } else {
                checkRetryCount(index, fileServerPaths, file, "重试次数超过" + maxRetry + "次了");
            }
        } catch (IOException e) {
            e.printStackTrace();
            checkRetryCount(index, fileServerPaths, file, "获取凭证失败");
        } catch (ClientException e) {
            e.printStackTrace();
            checkRetryCount(index, fileServerPaths, file, "OSS服务器初始化失败");
        } catch (ServiceException e) {
            e.printStackTrace();
            checkRetryCount(index, fileServerPaths, file, "OSS服务器操作失败");
        } catch (Exception e) {
            e.printStackTrace();
            checkRetryCount(index, fileServerPaths, file, "上传失败");
        }
    }

    /**
     * 请求oos 调用file.totken接口
     *
     * @param file
     * @return
     * @throws IOException
     * @throws UploadException
     */
    private OSSResult<FileUploadProvider> requestOSSToken(File file) throws IOException, UploadException {
        OSSResult<FileUploadProvider> result = null;
        LogUtil.i(LOG_TAG, "APP_ID:" + HttpConstants.AppId);

        Response response = HTTP.get()
                .api(HttpConstants.aliyun_oos_upload_apiToken)
                .addParam(APP_ID, HttpConstants.AppId)
                .addParam(FILE_TYPE, type)
                .addParam(BUSINESS_ID, businessId)
                .addParam(FILE_NAME, getFileName(file))
                .setHierarchy(2)
                .sign().execute(); //获取服务器中的token
        if (response != null && response.isSuccessful()) {
            String string = response.body().string();
            LogUtil.i(LOG_TAG, "requestOSSToken response:" + string);
            result = HSON.parse(string, new TypeToken<OSSResult<FileUploadProvider>>() {
            });
            HttpConfigController mHandler = HTTP.getController();
            if (mHandler != null && !mHandler.unitHandle(string)) { //后置检查
                return null;
            }
        } else {
            throw new UploadException("服务器请求失败");
        }
        return result;
    }

    private File handleCompressFile(File file) throws UploadException {
        //图片压缩处理
        if (compress && type == HttpConstants.FileTypeImage) {
            File compressedFile = compressFile(file);
            if (compressedFile != null && compressedFile.exists()) {
                file = compressedFile;
            }
        }
        return file;
    }

    @Nullable
    private File compressFile(File file) {
        //图片压缩处理
        double fileSize = FileUtil.getFileSize(file.getPath(), FileUtil.KB);
        if (fileSize > maxSize) { ///只有大于 maxSize kb的文件才需要进行压缩处理
            LogUtil.i("压缩前的大小: " + fileSize);
            String path = FileUtil.compressImage(appContext, file.getPath(), reqW, reqH, maxSize);
            //保存图片
            if (path == null) return file;
            File endFile = new File(path);
            double endFileSize = FileUtil.getFileSize(file.getPath(), FileUtil.KB);
            LogUtil.w("压缩后的大小: " + endFileSize);
            return endFile;
        }
        return file;
    }


    private CompleteMultipartUploadResult multipartUploadFile(File file, FileUploadProvider data, OSS ossClient) throws ClientException, ServiceException, IOException {
        String uploadId;
        uploadId = initMultipartUpload(data, ossClient);
        List<PartETag> partETags = uploadMultipart(file, data, ossClient, uploadId);
        CompleteMultipartUploadResult completeResult = completeMultipartUpload(data, ossClient, uploadId, partETags);
        return completeResult;
    }

    private String initMultipartUpload(FileUploadProvider data, OSS ossClient) throws ClientException, ServiceException {
        String uploadId;
        InitiateMultipartUploadRequest init = new InitiateMultipartUploadRequest(data.bucket, data.objectKey);
        InitiateMultipartUploadResult initResult = ossClient.initMultipartUpload(init);
        uploadId = initResult.getUploadId();
        return uploadId;
    }

    @NonNull
    private List<PartETag> uploadMultipart(File file, FileUploadProvider data, OSS ossClient, String uploadId) throws IOException, ClientException, ServiceException {
        long partSize = 128 * 1024; // 设置分片大小
        int currentIndex = 1; // 上传分片编号，从1开始
        File uploadFile = file; // 需要分片上传的文件
        InputStream input = new FileInputStream(uploadFile);
        long fileLength = uploadFile.length();
        long uploadedLength = 0;
        List<PartETag> partETags = new ArrayList<>(); // 保存分片上传的结果
        while (uploadedLength < fileLength) {
            int partLength = (int) Math.min(partSize, fileLength - uploadedLength);
            byte[] partData = IOUtils.readStreamAsBytesArray(input, partLength); // 按照分片大小读取文件的一段内容
            UploadPartRequest uploadPart = new UploadPartRequest(data.bucket, data.objectKey, uploadId, currentIndex);
            uploadPart.setPartContent(partData); // 设置分片内容
            bindMultipartProgressCallback(uploadPart);
            UploadPartResult uploadPartResult = ossClient.uploadPart(uploadPart);
            partETags.add(new PartETag(currentIndex, uploadPartResult.getETag())); // 保存分片上传成功后的结果

            uploadedLength += partLength;
            currentIndex++;
        }
        return partETags;
    }


    private CompleteMultipartUploadResult completeMultipartUpload(FileUploadProvider data, OSS ossClient, String uploadId, List<PartETag> partETags) throws ClientException, ServiceException {
        CompleteMultipartUploadRequest complete = new CompleteMultipartUploadRequest(data.bucket, data.objectKey, uploadId, partETags);
        CompleteMultipartUploadResult completeResult = ossClient.completeMultipartUpload(complete);
        complete.setCallbackParam(data.callBack);
        complete.setCallbackVars(data.params);
        return completeResult;
    }


    private void parseOSSResult(int index, List<String> fileServerPaths, File file, PutObjectResult ossResult) throws UploadException {
        //解析结果数据
        String body = ossResult.getServerCallbackReturnBody();
        OSSResult<FileResponseProvider> uploadResult = HSON.parse(body, new TypeToken<OSSResult<FileResponseProvider>>() {
        });
        if (uploadResult.isSuccess()) {
//            fileServerPaths.add(uploadResult.data.fileName);
            fileServerPaths.add(uploadResult.data.url);
        } else {
            checkRetryCount(index, fileServerPaths, file, "重试次数超过" + maxRetry + "次了");
        }
    }

    private void parseCMUResult(int index, List<String> fileServerPaths, File file, CompleteMultipartUploadResult cmuResult) throws UploadException {
        //解析结果数据
        String body = cmuResult.getServerCallbackReturnBody();
        String objectKey = cmuResult.getObjectKey();
        if (StringUtil.isNotEmpty(objectKey)) {
            fileServerPaths.add(objectKey);
        } else {
            checkRetryCount(index, fileServerPaths, file, "重试次数超过" + maxRetry + "次了");
        }
    }

    /**
     * 请求oss服务器
     * @param file
     * @param data
     * @param ossClient
     * @return
     * @throws ClientException
     * @throws ServiceException
     */
    private PutObjectResult requestOSSServer(File file, FileUploadProvider data, OSS ossClient) throws ClientException, ServiceException {
        PutObjectRequest put = new PutObjectRequest(data.bucket, data.objectKey, file.getAbsolutePath());
        bindSimpleOSSProgressCallback(put);
        put.setCallbackParam(data.callBack);
        put.setCallbackVars(data.params);
        //同步上传图片
        return ossClient.putObject(put);
    }

    private long totalFileSize = 0L;

    private void handleOSSProgressCallback(long currentSize) {
        if (mProgressCallback != null) {
            mProgressCallback.onProgress(currentSize, totalFileSize);
        }
    }

    private long alreadyFileSize = 0L;

    private void bindSimpleOSSProgressCallback(PutObjectRequest put) {
        if (mProgressCallback == null) {
            return;
        }
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest putObjectRequest, long currentSize, long totalSize) {
                handleOSSProgressCallback(alreadyFileSize + currentSize);
                if (currentSize >= totalSize) {
                    alreadyFileSize += totalSize;
                }
            }
        });
    }

    private void bindMultipartProgressCallback(final UploadPartRequest uploadPart) {
        if (mProgressCallback == null) {
            return;
        }
        uploadPart.setProgressCallback(new OSSProgressCallback<UploadPartRequest>() {
            @Override
            public void onProgress(UploadPartRequest uploadPartRequest, long currentSize, long totalSize) {
                handleOSSProgressCallback(alreadyFileSize + currentSize);
                if (currentSize >= totalSize) {
                    alreadyFileSize += totalSize;
                }
            }
        });
    }

    private OSS getOSSClient(FileUploadProvider data) {
        OSSCredentialProvider credentialProvider = new OssRequestDataProvider(data.accessKeyId, data.accessKeySecret,
                data.securityToken, data.expiration);
        initOSSCfgs();
        return new OSSClient(appContext, data.endpoint, credentialProvider, mClientConfiguration);
    }

    private void checkRetryCount(int index, List<String> fileServerPaths, File file, String msg) throws UploadException {
        int retryCount = requestRetryFlags.get(index);
        if (retryCount < 2) {
            requestRetryFlags.put(index, ++retryCount);
            uploadFile(index, fileServerPaths, file);
        } else {
            throw new UploadException(msg);
        }
    }

    private String getFileName(File file) {
        String path = file.getAbsolutePath();
        return path.split("/")[path.split("/").length - 1];
    }
}
