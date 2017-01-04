package com.zbase.adapter;

import android.content.Context;
import android.view.View;

import com.zbase.view.SlidingMenu;


/**
 * 创建人：郑晓辉
 * 创建日期：2016/6/14
 * 描述：列表左滑出现菜单
 * 子类要实现findSlidingMenuId方法，内部类要继承SlidingMenuItemViewHolder
 * 外部recyclerView要调用onScrolled方法，如下
 * recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
@Override
public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
super.onScrolled(recyclerView, dx, dy);
adapter.onScrolled();
}
});
 */
public abstract class BaseSlidingMenuAdapter<T> extends ZBaseRecyclerAdapter<T> {

    private boolean isOpen = false;//item是否是打开的状态
    private SlidingMenu mSlideMenu;

    public BaseSlidingMenuAdapter(Context context) {
        super(context);
    }

    public abstract class SlidingMenuItemViewHolder extends ItemViewHolder {

        protected SlidingMenu slidingMenu;

        public SlidingMenuItemViewHolder(View view) {
            super(view);
            slidingMenu = (SlidingMenu) view.findViewById(findSlidingMenuId());
            slidingMenu.setOnMenuOpenListener(new SlidingMenu.OnMenuOpenListener() {
                @Override
                public void onMenuOpen() {
                    if (isOpen && mSlideMenu != slidingMenu && mSlideMenu != null) {
                        mSlideMenu.closeMenu();
                    }
                    mSlideMenu = slidingMenu;
                    isOpen = true;
                }
            });
        }

        protected abstract int findSlidingMenuId();

    }

    public void onScrolled() {
        if (isOpen) {
            mSlideMenu.closeMenu();
            isOpen = false;
        }
    }

}
