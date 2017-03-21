package com.zbase.adapter;

import android.content.Context;
import android.view.View;

import com.zbase.view.SlidingMenu;


/**
 * 创建人：郑晓辉
 * 创建日期：2016/6/14
 * 描述：列表左滑出现菜单
 * 子类要实现findSlidingMenuId方法，内部类要继承SlidingMenuItemViewHolder
 * 注意：
 * SlidingMenu控件子布局必须是2个
 * 布局中滑出的菜单必须设置固定宽度DP
 * ptrClassicFrameLayout.disableWhenHorizontalMove(true);//解决和横向滑动控件的冲突，比如viewpager，SlidingMenu,ViewFlow等。
 recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
@Override
public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
super.onScrolled(recyclerView, dx, dy);
adapter.closeMenu();
}
});
 删除项的成功后也要调用adapter.closeMenu();
 adapter最外层布局不能用padding，不然会导致滑出菜单不全。
 */
public abstract class BaseSlidingMenuAdapter<T> extends ZBaseRecyclerAdapter<T> {

    private SlidingMenu currentSlideMenu;//当前项的菜单，哪个项的菜单滑开了，就赋值给这个对象
    private boolean canSlideMenu;//是否可以滑出菜单

    public BaseSlidingMenuAdapter(Context context) {
        super(context);
        this.canSlideMenu = true;//默认true可以
    }

    public BaseSlidingMenuAdapter(Context context, boolean canSlideMenu) {
        super(context);
        this.canSlideMenu = canSlideMenu;
    }

    public abstract class SlidingMenuItemViewHolder extends ItemViewHolder {

        protected SlidingMenu slidingMenu;

        public SlidingMenuItemViewHolder(View view) {
            super(view);
            slidingMenu = (SlidingMenu) view.findViewById(findSlidingMenuId());
            slidingMenu.setOnMenuOpenListener(new SlidingMenu.OnMenuOpenListener() {
                @Override
                public void onMenuOpen() {
                    if (currentSlideMenu != slidingMenu && currentSlideMenu != null) {
                        currentSlideMenu.closeMenu();
                    }
                    currentSlideMenu = slidingMenu;
                }
            });
            if (canSlideMenu) {
                slidingMenu.setScrollable(true);
            } else {
                slidingMenu.setScrollable(false);
            }
        }
        protected abstract int findSlidingMenuId();
    }

    /**
     * 关闭侧滑菜单
     */
    public void closeMenu() {
        if (currentSlideMenu != null) {
            currentSlideMenu.closeMenu();
        }
    }
}
