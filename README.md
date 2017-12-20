# 组件化基础框架

## 使用ARouter实现组件化
项目中没有自己设计路由，而是使用阿里在github上面的一个开源项目ARouter，地址是：[ARouter](https://github.com/alibaba/ARouter/)

## 组件化的优点

* 避免重复造轮子，节省开发维护成本;
* 降低项目复杂性，提升开发效率;
* 多个团队公用同一个组件，在一定层度上确保了技术方案的统一性。

## 组件化开发的实现

### 配置

1. 大家按照ARouter的文档去配置一些东西，需要注意的是每一个模块都必须引入compile sdk，处理API SDk可以在Base sdk
里面引入就行。
2. Annotation sdk不用我们引入，他会自己自动依赖引入
3. 在开发的时候，每一个module 的分组都必须不同，或者将会有一个 类重复的错误。分组就是path的第一个"/"与第二个"/"之间。

### 思路

我们是需要拆分组件，不同的模块负责自己的业务实现，不和其他模块有依赖关系的存在。假如这个模块需要启动别的Activity或者
是调用别的模块方法，我们就通过ARouter提供的方法去实现。
模块解析与规则：

####模块解析
1. 基础组件层，顾名思义就是一些基础组件，包含了各种开源库以及和业务无关的各种自研工具库;
2. 业务组件层，这一层的所有组件都是业务相关的。
3. 业务Module层，在AS中每块业务对应一个单独的Module。比如商城，商家都可以当做一个Module。或者两个完全不项目整合。
每个单独的Module都必须准遵守我们自己的 MVP 架构
4. app主壳模块只包含路由模块依赖，不进行其他业务处理

####规则
1. 业务模块层的各业务模块之间的通讯跳转采用路由框架Router来实现（目前自己设计的路由缺点很多）; 
2. 业务组件层中单一业务组件只能对应某一项具体的业务，对于有个性化需求的对外部提供接口让调用方定制; 
3. 合理控制各组件和各业务模块的拆分粒度，太小的公有模块不足以构成单独组件或者模块的，我们先放到类似于Common Lib的组件中，
在后期不断的重构迭代中视情况进行进一步的拆分
4. 上层的公有业务或者功能模块可以逐步下沉，合理把握好度就好； 
5. 各层级之间严禁反向依赖，横向依赖关系需讨论慎重处理。

### 实践

#### 管理Router

我们使用一个ModuleManager，提供一个Map，Map是使用对应module的Provider的path作为key和value。以及相关的数据结构，用来对ARouter的进行二次封装和管理模块。
大概实现如下：
```
public class ModuleManager {
    private ModuleOptions options;
    private ModuleManager() {
    }
    private static class ModuleManagerHolder {
        private static final ModuleManager instance = new ModuleManager();
    }
    public static ModuleManager getInstance() {
        return ModuleManagerHolder.instance;
    }
    public void init(ModuleOptions options) {
        if (this.options == null && options != null) {
            this.options = options;
        }
    }
    public ModuleOptions getOptions() {
        return options;
    }
    public boolean hasModule(String key) {
        return options.hasModule(key);
    }
}
```
ModuleOptions就是具体管理那些包含那些模块的配置。该类是在App或者是测试module独立运行(后面提到)的时候进行初始化。例如:
```
public class CustomApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        initARouter();
    }
    private void initARouter() {
        if (LG.isDebug) {
            ARouter.openLog();
            ARouter.openDebug();
            ARouter.printStackTrace();
        }
        ARouter.init(this);
 ModuleOptions.ModuleBuilder builder = new ModuleOptions.ModuleBuilder(this)
                .addModule(IFontProvider.FONT_MAIN_SERVICE, IFontProvider.FONT_MAIN_SERVICE)
                .addModule(IHomesProvider.HOMES_MAIN_SERVICE,IHomesProvider.HOMES_MAIN_SERVICE)
                .addModule(IMainProvider.MAIN_MAIN_SERVICE,IMainProvider.MAIN_MAIN_SERVICE)
                .addModule(IMarketProvider.MARKET_MAIN_SERVICE,IMarketProvider.MARKET_MAIN_SERVICE)
                .addModule(IMeProvider.ME_MAIN_SERVICE,IMeProvider.ME_MAIN_SERVICE)
                .addModule(IMerchantProvider.MERCHANT_MAIN_SERVICE,IMerchantProvider.MERCHANT_MAIN_SERVICE);
        ModuleManager.getInstance().init(builder.build());
    }
}
```
这样子就完成了对改App或者是module的管理。

#### 管理服务

我们使用一个ServiceManager，用来获取不同模块的服务，即Provider。安装ARouter的文档，我们通过继承IProvider，编写一个对应模块的接口，提供接口方法，在对应模块实现该Provider。然后该Provider我们就是在ServiceManager里面进行管理和获取。比如：
home模块实现一个IHomeProvider，实现类是HomeProvider。
```
//接口
public interface IHomeProvider extends IBaseProvider {
    //Service
    String HOME_MAIN_SERVICE = "/home/main/service";
    //服务
    String HOMES_MAIN_SERVICE = "/homes/main/service";

    //作为Fragment被添加时候的key
    String HOMES_KEY_FRAGMENT = "homes_key_fragment";

    String HOMES_ACT_ANNOUNCEMENT = "/homes/act/announcement";

    String HOMES_ACT_CONSUMPTION_INTEGRAL = "/homes/act/consumption_integral";
    String HOMES_ACT_CONSUMPTION_RECORD = "/homes/act/consumption_record";
    String Homes_ACT_INTEGRAL_RECORD = "/homes/act/integral_record";


    String HOMES_ACT_BUY_BACK = "/homes/act/buy_back";
    String HOMES_ACT_BUY_BACK_RECORD = "/homes/act/buy_back_record";
    String HOMES_ACT_BUY_BACK_RECORD_DETAIL = "/homes/act/buy_back_record_detail";
    //home主页
    String HOME_ACT_HOME = "/home/act/home";
    String HOME_TABTYPE = "home_tab_type";
}
//实现类
@Route(path = IHomeProvider.HOME_MAIN_SERVICE)
public class HomeProvider implements IHomeProvider {
    private Context context;
    @Override
    public void init(Context context) {
        this.context = context;
    }
}
```
然后在ServiceManager中，
```
    //也可以使用自动注入，这里是手动发现并且调用
  public IModule1Provider getModule1Provider() {
        return module1Provider != null ? module1Provider : (module1Provider = ((IModule1Provider) MyRouter.newInstance(IModule1Provider.MODULE1_MAIN_SERVICE).navigation()));
    }
```
我们对本Project的所有服务进行管理。然后，在base当中，提供不同的Service，对Provider进行调用，同时提供Intent方法，去启动不同模块的Activity，比如：
```
//管理调用Provider的某一个特定模块的Service
public class HomeService {
    private static boolean hasModule() {
        return ModuleManager.getInstance().hasModule(IHomeProvider.HOME_MAIN_SERVICE);
    }
    public static void selectedTab(Activity activity, int position) {
        if (!hasModule()) return;
        ServiceManager.getInstance().getHomeProvider().selectedTab(activity, position);
    }
}
//管理该module的Activity跳转
public class HomeIntent {
    private static boolean hasModule() {
        return ModuleManager.getInstance().hasModule(IHomeProvider.HOME_MAIN_SERVICE);
    }
    public static void launchHome(int tabType) {
        //HomeActivity
        MyBundle bundle = new MyBundle();
        bundle.put(IHomeProvider.HOME_TABTYPE, tabType);
        MyRouter.newInstance(IHomeProvider.HOME_ACT_HOME)
                .withBundle(bundle)
                .navigation();
    }
}
```

#### Module可独立运行配置

经过这两个，我们就已经基本完成了项目组件化。但是对于组件化，我们还有一个特点，就是每一个module都是可以独立运行的，方便开发和调试。那么，我们应该怎么弄？
1. 我们提供两种环境，一个是debug（组件模式），一种是release（集成模式），debug的时候，我们是可运行的独立模块，release的时候，我们是library。
2. debug的时候，我们需要提供一些测试代码和一套初始化。
例如:我们需要module1是可以独立运行的，在本demo中，他是一个Fragment作为主入口被别的模块添加使用，所以我们的debug中需要添加一个Activity，一套清单，一些资源。
我们使用config.gradle去管理我们的一些外部依赖arr以及我们的一些编译版本号，sdk版本号等，如下：
```
ext {
       //...版本号以及arr管理
    //home是否是作为模块，true的时候是，false的时候可以独立运行
    isMouleDebugHome = true;
    //module1是否是作为模块，true的时候是，false的时候可以独立运行
    isModule1Debug = true;
}
```
然后，在跟build.gradle第一行中apply 进去。
```
apply from: "config.gradle"
```
然后，使用sourceSets对代码进行管理，配置debug和release的代码

gradle配置如下：
```
if (rootProject.ext.isModule1Debug) {
    apply plugin: 'com.android.library'
} else {
    apply plugin: 'com.android.application'
}
android {
    compileSdkVersion rootProject.ext.android.compileSdkVersion
    buildToolsVersion rootProject.ext.android.buildToolsVersion
    defaultConfig {
        minSdkVersion rootProject.ext.android.minSdkVersion
        targetSdkVersion rootProject.ext.android.targetSdkVersion
        versionCode 101
        versionName "1.0.1"
        if (!rootProject.ext.isModule1Debug) {
            applicationId "com.github.io.liweijie.lib1"
        }
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = [moduleName: project.getName()]
            }
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_7
        targetCompatibility JavaVersion.VERSION_1_7
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
    sourceSets {
        main {
            if (!rootProject.ext.isModule1Debug) {
                manifest.srcFile 'src/debug/AndroidManifest.xml'
                java.srcDir 'src/debug/java/'
                res.srcDirs=['src/debug/res']
            } else {
                manifest.srcFile 'src/release/AndroidManifest.xml'
                java.srcDir 'src/release/java/'
            }
        }
    }
}
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
        exclude group: 'com.android.support', module: 'support-annotations'
    })
    compile project(':base')
    testCompile 'junit:junit:4.12'
    annotationProcessor rootProject.ext.dependencies["aroutercompiler"]
}
```
这里需要注意的问题是，debug和relase的清单都需要声明需要的activity以及其他组件。debug中还应该配置Application，
进行ARouter的初始化。经过这样子，我们的每一个module都是可以独立运行的模块了。一般而言，release其实是没有什么东西的，
因为release需要的就是我们module本身需要的业务逻辑实现代码，他是作为library去使用的，看自己项目是否需要配置relase。
在本项目中，修改对应的dependencies.gradle中的值就可以使得module1和home独立运行或者是作为lib添加。

#### AndroidManifest合并
        main {
            if (!rootProject.ext.isModule1Debug) {
                manifest.srcFile 'src/debug/AndroidManifest.xml'
                java.srcDir 'src/debug/java/'
                res.srcDirs=['src/debug/res']
            } else {
                manifest.srcFile 'src/release/AndroidManifest.xml'
                java.srcDir 'src/release/java/'
            }
        }


## 组件化开发的建议

1. 后期，在每一个模块文档之后，我们应该使用arr的形式引入依赖，上传我们的maven库，再去compile下来，同时注释掉setting.gradle的配置，这样子有助于编译加快。
2. 删除各个module的test代码，也就是src目录下的test，因为那些都是包含一些task的，在同步或者是编译的时候会被执行，减慢了编译速度。
3. 四大组件应该在各自module里面声明。
4. 在Base中提供一个BaseApplication。BaseApplication 主要用于各个业务组件和app壳工程中声明的 Application 类继承用的，
只要各个业务组件和app壳工程中声明的Application类继承了 BaseApplication，当应用启动时 BaseApplication 就会被动实例化，
这样从 BaseApplication 获取的 Context 就会生效，也就从根本上解决了我们不能直接从各个组件获取全局 Context 的问题；

## 一些经验
1. 不要一步彻底的组件化，步子不要迈得太大，建议是先将比较容易的组件进行拆分出来，先做成模块，然后一边进行业务的迭代，两边都不耽误，
除非你有足够时间进行组件化工作才去彻底组件化。
2. 关于资源的拆分，一些style，一些常见的string，一些共用的图片，drawable等资源，建议存放在base module当中，可以共用。
对于属于不同模块的资源，建议不要存放在base，可能一开始你直接把所有的资源都放到base里面会比较好做，比如不用担心编译错误，
资源无法寻找到，需要一个一个的复制的问题，但是，你存放到了base，而base模块，一般开发过程中，是经常变动的，那么，
就是他会经常被编译，就会带来一个编译的速度问题，所以应该各个模块存放自己的资源。
3. 关于Base的依赖库问题，比如我们可能有自家的sdk，第三方的依赖等等，这些，与业务逻辑无关的依赖，
建议是使用一个单独的project进行二次封装，封装完成之后，打包arr 依赖，上传自家maven库，通过compile 进行base，
或者是特定模块，不建议直接在app中新建module。比如，我们要引入volley，我们应该使用一个新的project，对volley进行二次封装，
稳定之后，通过compile arr 引入base 或者是其他模块中。这样子这些与业务逻辑无关的也方便公司其他项目使用。
4. 针对旧项目拆分的module，可以暂时不编写debug，也就是可以不用使得旧项目可以独立运行，因为他涉及的初始化逻辑可能较多，
时间不够，直接被app依赖运行测试。针对新的module，可以编写debug来使得新module独立运行，方便调试。

## 组件化带来的问题

1. 由于项目的组价话，可能需要每个业务拆分的比较开，那么将会导致module也比较的多。到了最后，
可能是一个模块带一个Activity以及一个Fragment作为一个组件，那么将会比较难的开发，所以，模块的拆分，
我们需要按照我们实际项目进行划分模块，不要划分太细致。同时，在旧项目组件化过程中，由于各个模块尚未成熟，
还没有稳定，那么就会有比较多的模块进行编译，速度也是比较慢的，所以旧项目的拆分也是需要按照一定的步骤慢慢拆分。
同时，建议使用Activity+Fragment的方式进行组件化，方便被别的组件使用，Activity不涉及一般逻辑。
2. 有时候修改base库，需要修改依赖方式会比较的麻烦。
3. 有时候会有资源冲突问题,这个我们在gradle里面为模块资源添加一个前缀解决



## 过程中遇到的问题
1. butterknife 不能再module中使用
<br>解决方法： 在app/build.gradle中配置：
```   
   repositories {
        mavenCentral()
        。。。。
        maven { url 'https://oss.sonatype.org/content/repositories/snapshots' }
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.0.0'
        classpath 'com.jakewharton:butterknife-gradle-plugin:9.0.0-SNAPSHOT'

    }
}
```
然后在每一个module中加上：
```
apply plugin: 'com.android.library'
apply plugin: 'com.jakewharton.butterknife'

```
在基module中加上：
```
   api rootProject.ext.dependencies["butterknife"]
```
在其他引用的Module上使用
```
    annotationProcessor rootProject.ext.dependencies["butterknife-compiler"]
```
切记不能只在基module中直接加上这两个配置。目前只有这上述方法才能解决R2空指针问题。

2. butterknife在module中是R2,只能在annotation中使用,不能直接onclick注册事件
3. 像这样使用：
```
@OnClick({R2.id.textView, R2.id.button1, R2.id.button2, R2.id.button3, R2.id.image})
  public void onViewClicked(View view) {
      int i = view.getId();
      if (i == R.id.textView) {
      } else if (i == R.id.button1) {
      } else if (i == R.id.button2) {
      } else if (i == R.id.button3) {
      } else if (i == R.id.image) {
      }
  }
或者：
@OnClick({R2.id.textView, R2.id.button1, R2.id.button2, R2.id.button3, R2.id.image})
  public void onViewClicked(View view) {
      switch (view.getId()) {
          case R.id.textView:
              break;
          case R.id.button1:

              break;
          case R.id.button2:
              break;
          case R.id.button3:
              break;
          case R.id.image:
              break;
      }
  }


```
4. 清单合并问题
每一个组件都会有对应的 AndroidManifest.xml，用于声明需要的权限、Application、Activity、Service、Broadcast等，当项目处于组件模式时，
业务组件的 AndroidManifest.xml 应该具有一个 Android APP 所具有的的所有属性，尤其是声明 Application 和要 launch的Activity，
但是当项目处于集成模式的时候，每一个业务组件的 AndroidManifest.xml 都要合并到“app壳工程”中，要是每一个业务组件都有自己的 Application 
和 launch的Activity，那么合并的时候肯定会冲突。所以：
业务组件的表单是绝对不能拥有自己的 Application 和 launch 的 Activity的，也不能声明APP名称、图标等属性，总之app壳工程有的属性，业务组件都不能有

5. lib重复问题
其实AS会自动过滤合并重复引用第三方库的。但是当我们使用的第三库中含有AndroidSupport 与我们本身引用的有版本冲突问题，只需要使用：
```
        exclude module: 'support-v4'//根据组件名排除
        exclude group: 'android.support.v4'//根据包名排除
```
6. 详细介绍一下app壳模块
<br>a. application 必须继承base中的baseApplication。
<br>b. 壳 AndroidManifest.xml 是根清单文件，其他组件的清单文件会合并到这个文件中
<br>c. build.gradle 需要在这个壳文件中进行app签名打包配置已经 Module引用问题

7. main组件：属于业务组件，指定APP启动页面、主界面

8. 设置了resourcePrefix值后，所有的资源名必须以指定的字符串做前缀，否则会报错。但是resourcePrefix这个值只能限定xml里面的资源，
并不能限定图片资源，所有图片资源仍然需要手动去修改资源名。
9. 组件化项目中采用在集成模式下集中在app壳工程中混淆，各个业务组件不配置混淆文件
10. 当单独运行某一个Module时需要添加签名相关配置，因为里面已经添加productFlavors配置两关。