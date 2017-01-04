//package com.zbase.view;
//
//import android.graphics.Rect;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//
//import com.ving.mtdesign.Constants;
//import com.ving.mtdesign.R;
//
//
///**
// * Created by Administrator
// * date 2015/6/15
// * Description: 作品间距
// */
//public class ProductMultGridSpacingItemDecoration extends RecyclerView.ItemDecoration {
//    private int spacing;
//
//    @Override
//    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        super.getItemOffsets(outRect, view, parent, state);
//        if (spacing == 0) {
//            spacing = view.getResources().getDimensionPixelOffset(R.dimen.re_item_material_img_pandding);
//        }
//        int halfSpacing = spacing / 2;
//
//        int itemType = parent.getChildViewHolder(view).getItemViewType();
//        if (itemType == Constants.ItemViewType0) {
//            return;
//        }
//        if (itemType == Constants.ItemViewType1) {
//            outRect.top = halfSpacing;
//            outRect.bottom = halfSpacing;
//            return;
//        }
//
//        if (itemType != Constants.ItemViewType2) {
//            return;
//        }
//
//        GridLayoutManager mgr = (GridLayoutManager) parent.getLayoutManager();
////        int childCount = parent.getChildCount();
//        int childIndex = parent.getChildAdapterPosition(view);
//        int spanCount = mgr.getSpanCount();
//        int spanIndex = mgr.getSpanSizeLookup().getSpanIndex(childIndex, spanCount);
//        /* INVALID SPAN */
//        if (spanCount < 1) return;
//
//
//        outRect.top = halfSpacing;
//        outRect.bottom = halfSpacing;
//        outRect.left = halfSpacing;
//        outRect.right = halfSpacing;
////            outRect.bottom = spacing;
//
//        if (isLeftEdge(spanIndex)) {
//            outRect.left = spacing;
//        }
//
//        if (isRightEdge(spanIndex, spanCount)) {
//            outRect.right = spacing;
//        }
//
//        /**
//         * 存在 Banner View childIndex - 1
//         */
//        if (isTopEdge(childIndex - 1, spanCount)) {
//            outRect.top = spacing;
//        }
//
////        if (isBottomEdge(childIndex, childCount, spanCount)) {
////            outRect.bottom = spacing;
////        }
//    }
//
////    protected int getTotalSpan(RecyclerView parent) {
////        RecyclerView.LayoutManager mgr = parent.getLayoutManager();
////        if (mgr instanceof GridLayoutManager) {
////            return ((GridLayoutManager) mgr).getSpanCount();
////        } else if (mgr instanceof StaggeredGridLayoutManager) {
////            return ((StaggeredGridLayoutManager) mgr).getSpanCount();
////        }
////
////        return -1;
////    }
////
////    protected int getSpanIndex(int position, RecyclerView parent) {
////        RecyclerView.LayoutManager mgr = parent.getLayoutManager();
////        if (mgr instanceof GridLayoutManager) {
////            return ((GridLayoutManager) mgr).getSpanSizeLookup().
////                    getSpanIndex(
////                            position,
////                            ((GridLayoutManager) mgr).getSpanCount()
////                    );
////        } else if (mgr instanceof StaggeredGridLayoutManager) {
////            return ((StaggeredGridLayoutManager) mgr).getSpanCount();
////        }
////
////        return -1;
////    }
//
//    protected boolean isLeftEdge(int spanIndex) {
//        return spanIndex == 0;
//    }
//
//    protected boolean isRightEdge(int spanIndex, int spanCount) {
//        return spanIndex == spanCount - 1;
//    }
//
//    protected boolean isTopEdge(int childIndex, int spanCount) {
//        return childIndex < spanCount;
//    }
//
//    protected boolean isBottomEdge(int childIndex, int childCount, int spanCount) {
//        return childIndex >= childCount - spanCount;
//    }
//
//}
