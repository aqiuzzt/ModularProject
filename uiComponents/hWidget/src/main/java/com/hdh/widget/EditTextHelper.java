package com.hdh.widget;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.util.SparseBooleanArray;
import android.widget.EditText;





/**
 *  编辑框帮助类
 */
public class EditTextHelper {
    private SparseBooleanArray mSparseBooleanArray;

    public void addEditTextWithMinLength(@NonNull final EditEnableText... editTexts) {
        if (mSparseBooleanArray == null) {
            mSparseBooleanArray = new SparseBooleanArray(editTexts.length);
        }
        for (int i = 0; i < editTexts.length; i++) {
            mSparseBooleanArray.put(i, false);
            final EditEnableText edit = editTexts[i];
            final int finalIndex = i;
            TextWatcherAdapter mTextWatcher = new TextWatcherAdapter() {
                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() < edit.getMinLength())
                        mSparseBooleanArray.put(finalIndex, false);
                    else {
                        mSparseBooleanArray.put(finalIndex, true);
                    }
                    if (mOnTextChangeListener != null) {
                        mOnTextChangeListener.onAllMeet(isAllMeetTheConditions());
                    }
                }
            };
            edit.getEditText().addTextChangedListener(mTextWatcher);
        }
    }

    private boolean isAllMeetTheConditions() {
        if (mSparseBooleanArray == null) return true;
        for (int i = 0; i < mSparseBooleanArray.size(); i++) {
            if (!mSparseBooleanArray.get(i, false)) return false;
        }
        return true;
    }

    // TODO: 2017/3/29
    public void release() {
    }

    public interface OnTextChangeListener {
        void onAllMeet(boolean meet);
    }

    private OnTextChangeListener mOnTextChangeListener;

    public void setOnTextChangeListener(OnTextChangeListener onTextChangeListener) {
        mOnTextChangeListener = onTextChangeListener;
    }

    public static class EditEnableText {
        private EditText mEditText;
        private int minLength = 1;

        public EditEnableText(EditText editText, int minLength) {
            mEditText = editText;
            this.minLength = minLength;
        }

        public EditEnableText(EditText editText) {
            mEditText = editText;
        }

        public EditText getEditText() {
            return mEditText;
        }

        public int getMinLength() {
            return minLength;
        }
    }

    private static class TextWatcher extends TextWatcherAdapter {
        private int minLength = 1;
        private int index;
        private OnTextChangeListener mOnTextChangeListener;
        private SparseBooleanArray mSparseBooleanArray;

        public TextWatcher(int minLength, int index, OnTextChangeListener onTextChangeListener, SparseBooleanArray sparseBooleanArray) {
            this.minLength = minLength;
            this.index = index;
            mOnTextChangeListener = onTextChangeListener;
            mSparseBooleanArray = sparseBooleanArray;
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() < minLength)
                mSparseBooleanArray.put(index, false);
            else {
                mSparseBooleanArray.put(index, true);
            }
            if (mOnTextChangeListener != null) {
                mOnTextChangeListener.onAllMeet(isAllMeetTheConditions());
            }
        }

        private boolean isAllMeetTheConditions() {
            if (mSparseBooleanArray == null) return true;
            for (int i = 0; i < mSparseBooleanArray.size(); i++) {
                if (!mSparseBooleanArray.get(i, false)) return false;
            }
            return true;
        }
    }
}
