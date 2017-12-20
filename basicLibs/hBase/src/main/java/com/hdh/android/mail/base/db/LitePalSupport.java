package com.hdh.android.mail.base.db;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

/**
 * Desc:实体类必须集成自{@link LitePalSupport} 其中包含一个LitePal需要保持的id(自增长),不要与服务器的id混淆
 * 如果服务器中返回了id那么应该用{@link com.google.gson.annotations.SerializedName}进行转换,否则会覆盖服务器中的id,导致数据错误
 *
 * @see DataSupport
 */

public class LitePalSupport extends DataSupport {
    @Expose(serialize = false, deserialize = false)
    @SerializedName("litePalId")
    public long id;
}
