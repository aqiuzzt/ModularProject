package com.hdh.android.mail.base.inte;

import com.hdh.common.util.CheckUtil;

/**
 * presenter实现类
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/8 18:55
 */
public abstract class PresenterImpl<V extends IActivityView, M extends IModel> implements IPresenter {

    private M mModel;
    private V mView;

    /**
     * 这里实现其他model 初始化
     */
    private PresenterImpl() {
    }

    public PresenterImpl(V v) {
        mModel = createModel();
        CheckUtil.checkNotNull(v);
        this.mView = v;
        if (v instanceof IFragmentView) {
            ((IFragmentView) v).setPresenter(this);
        }
    }

    protected abstract M createModel();

    protected M getModel() {
        return mModel;
    }

    protected V getView() {
        return mView;
    }

    @Override
    public void onDetach() {
        mView = null;
    }

    public boolean checkNull() {
        return CheckUtil.checkNull(mView);
    }

}
