package com.aopcloud.palmproject.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.aopcloud.palmproject.R;

public class InputDialog {
    protected final String TAG = this.getClass().getSimpleName();
    private Dialog mDialog;
    private Activity ac;
    private View rootView;
    private TextView tv_title;

    public InputDialog(Activity ac){
        this(ac, -1);
    }

    public InputDialog(Activity ac, @LayoutRes int layoutId){
        if (finishing(ac)){
            return;
        }

        this.ac = ac;
        builder(layoutId);
    }

    private void builder(@LayoutRes int layoutId) {
        mDialog = new Dialog(ac, R.style.CommonDialogStyle);
        Window window = mDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = (int)(getScreenWidth(ac) * 0.8);
            params.height = getScreenHeight(ac);
            window.setGravity(Gravity.CENTER);
            params.x = 0;
            params.y = 0;
            window.setAttributes(params);
        }
        mDialog.setContentView(getContentView(layoutId));
    }

    protected Window getDialogWindow(){
        return mDialog == null ? null : mDialog.getWindow();
    }

    @SuppressLint("ResourceType")
    @NonNull
    private View getContentView(@LayoutRes int layoutId) {
        if (layoutId <= 0){
            layoutId = R.layout.dialog_input_default;
        }
        rootView = View.inflate(ac, layoutId, null);
        return rootView;
    }

    public InputDialog setTitle(String msg){
        if (msg == null){
            return this;
        }

        if (tv_title == null){
            if (rootView != null){
                tv_title = (TextView)rootView.findViewById(R.id.tv_title);
            }
        }
        if (tv_title != null){
            tv_title.setText(msg);
        }
        return this;
    }

    private EditText ed_input;
    public InputDialog setMsg(String msg) {
        if (msg == null) {
            return this;
        }
        if (ed_input == null) {
            if (rootView != null){
                ed_input = (EditText)rootView.findViewById(R.id.ed_input);
            }
        }
        if (ed_input != null){
            ed_input.setText(msg);
            ed_input.setSelection(msg.length());
            getDialogWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            ed_input.requestFocus();
        }
        return this;
    }

    public void show() {
        if (!finishing(ac) && mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
    }

    public boolean isShowing(){
        if (!finishing(ac) && mDialog != null){
            return mDialog.isShowing();
        }else {
            return false;
        }
    }

    public Dialog getDialog(){
        return mDialog;
    }

    public void dismiss() {
        if (!finishing(ac) && mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public InputDialog setCancelable(boolean cancel) {
        if (!finishing(ac) && mDialog != null) {
            mDialog.setCancelable(cancel);
        }
        return this;
    }

    public InputDialog setCanceledOnTouchOutside(boolean cancel) {
        if (!finishing(ac) && mDialog != null) {
            mDialog.setCanceledOnTouchOutside(cancel);
        }
        return this;
    }

    public void setOnDisListener(DialogInterface.OnDismissListener l){
        if (!finishing(ac) && mDialog != null) {
            if (l != null) {
                mDialog.setOnDismissListener(l);
            }
        }
    }
    public InputDialog setCancelBtn(String text, final OnClickListener listener){
        TextView tv_left = (TextView) getChildView(R.id.tv_cancel);
        if (tv_left != null) {
            if (text != null) {
                tv_left.setText(text);
            }
            if (listener != null) {
                tv_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClick(getDialog(), v, null);
                    }
                });
            }
        }
        return this;
    }
    public InputDialog setCancelBtn(OnClickListener listener){
        return setCancelBtn(null, listener);
    }

    public InputDialog setCommitBtn(String text, final OnClickListener listener){
        TextView tv_right = (TextView) getChildView(R.id.tv_ok);
        if (tv_right != null) {
            if (text != null) {
                tv_right.setText(text);
            }
            if (listener != null) {
                tv_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null){
                            String input = null;
                            if (ed_input != null) {
                                input = ed_input.getText().toString();
                            }
                            listener.onClick(getDialog(), v, input);
                        }
                    }
                });
            }
        }
        return this;
    }
    public InputDialog setCommitBtn(OnClickListener listener){
        return setCommitBtn(null, listener);
    }

    public View getChildView(@IdRes int childViewId){
        if (rootView != null){
            return rootView.findViewById(childViewId);
        }
        return null;
    }

    private boolean finishing(Activity ac) {
        if (isNull(ac) || ac.isFinishing()) {
            return true;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (ac.isDestroyed()) {
                return true;
            }
        }
        return false;
    }

    private boolean isNull(Activity ac) {
        return ac == null;
    }

    private int getScreenWidth(Activity ac) {
        if (isNull(ac)) {
            return -1;
        }

        DisplayMetrics dm = new DisplayMetrics();
        ac.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    private int getScreenHeight(Activity ac) {
        if (isNull(ac)) {
            return -1;
        }

        DisplayMetrics dm = new DisplayMetrics();
        ac.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public interface OnClickListener{
        void onClick(Dialog dialog, View view, CharSequence input);
    }

}
