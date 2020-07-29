package com.aopcloud.palmproject.dialog;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.dialog.adapter.RvBottomListMulAdapter;
import com.aopcloud.palmproject.dialog.bean.MulBean;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

public class BottomListMulDialog {

    public BottomListMulDialog(Context cx, List<MulBean> datas) {
        this(cx, true, datas);
    }
    public BottomListMulDialog(Context cx, boolean mulSelect, final List<MulBean> datas) {
        BottomSheetDialog dialog = new BottomSheetDialog(cx);
        dialog.setContentView(R.layout.dialog_bottom_list);
        dialog.getWindow().findViewById(R.id.design_bottom_sheet)
                .setBackgroundResource(android.R.color.transparent);
        dialog.setCancelable(true);
        dialog.show();

        RecyclerView rv = dialog.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(cx));


        RvBottomListMulAdapter adapter = new RvBottomListMulAdapter(datas);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                select(mulSelect, datas, position);
                adapter.notifyLoadMoreToLoading();
            }
        });

        dialog.findViewById(R.id.tv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onCancel(view);
                }
            }
        });

        dialog.findViewById(R.id.tv_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onCommit(view);
                }
            }
        });
    }

    private void select(boolean mulSelect, List<MulBean> datas, int position){
        if (datas == null){
            return;
        }
        if (position < 0) {
            return;
        }
        if (position > datas.size() - 1) {
            return;
        }

        boolean oldStatus = datas.get(position).isSelected();
        if (oldStatus) {
            datas.get(position).setSelected(false);
            return;
        }

        if (!mulSelect) {
            for (MulBean b : datas) {
                b.setSelected(false);
            }
            datas.get(position).setSelected(true);
        } else {
            datas.get(position).setSelected(!oldStatus);
        }
    }

    public interface OnClickListener {
        void onCommit(View view);
        void onCancel(View view);
    }

    private OnClickListener onClickListener;
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}