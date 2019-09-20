package com.shengma.lanjing.dialogs;


import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.adapters.YongHuListAdapter;
import com.shengma.lanjing.beans.YongHuListBean;
import com.shengma.lanjing.beans.YongHuListBean_;
import java.util.ArrayList;
import java.util.List;
import io.objectbox.Box;



public class YongHuListDialog extends DialogFragment {

    private Window window;
    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private YongHuListAdapter yongHuListAdapter;
    private List<YongHuListBean> yongHuListBeanList=new ArrayList<>();
    private Box<YongHuListBean> yongHuListBeanBox= MyApplication.myApplication.getYongHuListBeanBox();
    private int size=20,page=0;
    private boolean isZB=false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // 去掉默认的标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View  view = inflater.inflate(R.layout.yonghu_dialog, null);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        recyclerView = view.findViewById(R.id.recyclerView);
        ImageView fanhui=view.findViewById(R.id.fanhui);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        LinearLayoutManager manager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        yongHuListAdapter=new YongHuListAdapter(yongHuListBeanList);
        recyclerView.setAdapter(yongHuListAdapter);
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG, "position:" + position);
                if (isZB){//是主播才能点击
                    YongHuXinxiDialog yongHuXinxiDialog=new YongHuXinxiDialog(yongHuListBeanList.get(position).getId()+"");
                    yongHuXinxiDialog.show(getFragmentManager(),"yonghuxnxi");
                    dismiss();
                }
            }
        });
       // refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                page+=1;
                List<YongHuListBean> listBeans= yongHuListBeanBox.query().orderDesc(YongHuListBean_.jingbi).build().find(size*page,size);
                yongHuListBeanList.addAll(listBeans);
                yongHuListAdapter.notifyDataSetChanged();
                refreshlayout.finishLoadMore(600/*,false*/);//传入false表示加载失败
            }
        });

      yongHuListBeanList.clear();
      List<YongHuListBean> listBeans= yongHuListBeanBox.query().orderDesc(YongHuListBean_.jingbi).build().find(size*page,size);
      yongHuListBeanList.addAll(listBeans);
      yongHuListAdapter.notifyDataSetChanged();
       // Log.d("YongHuListDialog", "yongHuListBeanList.size():" + yongHuListBeanList.size());
        return view;
    }

    public void setzhu(boolean isZB){
        this.isZB=isZB;
    }

    @Override
    public void onStart() {
        super.onStart();
        // 下面这些设置必须在此方法(onStart())中才有效
        window = getDialog().getWindow();
        if (window!=null){
            // 如果不设置这句代码, 那么弹框就会与四边都有一定的距离
            window.setBackgroundDrawableResource(android.R.color.transparent);
            // 设置动画
            window.setWindowAnimations(R.style.bottomDialog);
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.BOTTOM;
            // 如果不设置宽度,那么即使你在布局中设置宽度为 match_parent 也不会起作用
            params.width = getResources().getDisplayMetrics().widthPixels;
            window.setAttributes(params);
        }

    }





}
