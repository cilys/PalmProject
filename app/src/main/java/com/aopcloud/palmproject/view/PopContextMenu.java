package com.aopcloud.palmproject.view;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.bean.PopMenuBean;
import com.aopcloud.palmproject.common.PopContextMenuAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.view
 * @File : PopContextMenu.java
 * @Date : 2020/5/3 2020/5/3
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class PopContextMenu {
    private Context mContext;
    private List<PopMenuBean> itemList;
    private PopupWindow popupWindow;
    private View contentView;
    private ListView mLvMenuList;
    private PopContextMenuAdapter menuAdapter;
    private OnItemSelectListener onItemSelectListener;
    private static final int DEFAULT_HEIGHT =  ListView.LayoutParams.WRAP_CONTENT;
    private int popHeight = DEFAULT_HEIGHT;
    private int popWidth = ListView.LayoutParams.WRAP_CONTENT;
    private boolean showIcon = true;
    private boolean dimBackground = true;
    private boolean needAnimationStyle = true;

    private static final int DEFAULT_ANIM_STYLE = R.style.PopContextMenu;
    private int animationStyle=DEFAULT_ANIM_STYLE;

    private float alpha = 0.5f;

    public interface OnItemSelectListener{
        void onItemSelect(int position);
    }

    public PopContextMenu setOnItemSelectListener(OnItemSelectListener onItemSelectListener){
        this.onItemSelectListener = onItemSelectListener;
        return this;
    }

    public PopContextMenu(Context  mContext){
        this.mContext = mContext;
        itemList = new ArrayList<>();
        initPopWindow();
    }

    /**
     * 初始化popwindow菜单
     */
    private void initPopWindow(){
        contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_context_menu, null);
        popupWindow = new PopupWindow(contentView);
        popupWindow.setHeight(popHeight);
        popupWindow.setWidth(popWidth);

        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        if (needAnimationStyle){
            popupWindow.setAnimationStyle(animationStyle <= 0 ? DEFAULT_ANIM_STYLE : animationStyle);
        }
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (dimBackground) {
                    setBackgroundAlpha(alpha, 1f, 300);
                }
            }
        });
        mLvMenuList = contentView.findViewById(R.id.lv_menu);
        menuAdapter = new PopContextMenuAdapter(mContext, R.layout.item_context_menu,itemList);
        mLvMenuList.setAdapter(menuAdapter);
        mLvMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onItemSelectListener != null){
                    onItemSelectListener.onItemSelect(position);
                }
                if (dimBackground) {
                    setBackgroundAlpha(alpha, 1f, 300);
                }
                popupWindow.dismiss();
            }
        });
    }

    private void initPw() {
        final Activity activity = (Activity) mContext;
        //设置半透明
        WindowManager.LayoutParams lp=  activity.getWindow().getAttributes();
        lp.alpha = 0.4f;
        activity.getWindow().setAttributes(lp);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.update();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            //在dismiss中恢复透明度
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp =  activity. getWindow().getAttributes();
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                lp.alpha = 1f;
                activity.getWindow().setAttributes(lp);
            }
        });
    }

    public PopContextMenu showMenu(View anchor){
        showMenu(anchor, 0, 0);
        return this;
    }

    public PopContextMenu showMenu(View anchor, int xoff, int yoff){
        if (popupWindow == null){
            initPopWindow();
        }
        if (!popupWindow.isShowing()) {
            popupWindow.showAsDropDown(anchor, xoff, yoff);
            if (dimBackground){
                setBackgroundAlpha(1f, alpha, 240);
            }
        }
        return this;
    }

    private void setBackgroundAlpha(float from, float to, int duration) {
        final Activity activity = (Activity) mContext;
        final WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        ValueAnimator animator = ValueAnimator.ofFloat(from, to);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                lp.alpha = (float) animation.getAnimatedValue();
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                activity.getWindow().setAttributes(lp);
            }
        });
        animator.start();
    }


    public void dismiss(){
        if (popupWindow != null && popupWindow.isShowing()){
            popupWindow.dismiss();
        }
    }

    public PopContextMenu setHeight(int height){
        if (height <= 0 && height != ListView.LayoutParams.MATCH_PARENT
                && height != ListView.LayoutParams.WRAP_CONTENT){
            this.popHeight = DEFAULT_HEIGHT;
        }else {
            this.popHeight = height;
        }
        return this;
    }

    public PopContextMenu setWidth(int width){
        if (width <= 0 && width != ListView.LayoutParams.MATCH_PARENT){
            this.popWidth = ListView.LayoutParams.WRAP_CONTENT;
        }else {
            this.popWidth = width;
        }
        return this;
    }

    /**
     * 添加单个菜单
     * @param item
     * @return
     */
    public PopContextMenu addMenuItem(PopMenuBean item){
        itemList.add(item);
        return this;
    }

    /**
     * 添加多个菜单
     * @param list
     * @return
     */
    public PopContextMenu addMenuList(List<PopMenuBean> list){
        itemList.addAll(list);
        return this;
    }

    /**
     * 是否让背景变暗
     * @param b
     * @return
     */
    public PopContextMenu dimBackground(boolean b){
        this.dimBackground = b;
        return this;
    }

    /**
     * 否是需要动画
     * @param need
     * @return
     */
    public PopContextMenu needAnimationStyle(boolean need){
        this.needAnimationStyle = need;
        return this;
    }

    /**
     * 设置动画
     * @param style
     * @return
     */
    public PopContextMenu setAnimationStyle(int style){
        this.animationStyle = style;
        return this;
    }

}
