package com.zbase.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/4/14
 * 描述：通用树形结构抽象父类
 */
public abstract class TreeNode {

    protected TreeNode parentTreeNode;//父节点
    protected List<TreeNode> childTreeNodeList = new ArrayList<>();//子节点
    protected boolean selected;//是否是选中的节点
    protected StringBuilder builder;

    public TreeNode getParentTreeNode() {
        return parentTreeNode;
    }

    protected void setParentTreeNode(TreeNode parentTreeNode) {
        this.parentTreeNode = parentTreeNode;
    }

    public List<TreeNode> getChildTreeNodeList() {
        return childTreeNodeList;
    }

    public void setChildTreeNodeList(List<TreeNode> childTreeNodeList) {
        this.childTreeNodeList = childTreeNodeList;
        for (int i = 0; i < childTreeNodeList.size(); i++) {
            childTreeNodeList.get(i).setParentTreeNode(this);
        }
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * 获取父节点和兄弟节点集合，爷爷节点的子节点，就是上一层的数据列表了
     *
     * @param currentTreeNode
     * @return
     */
    public List<TreeNode> getParentAndBrothers(TreeNode currentTreeNode) {
        return currentTreeNode.getParentTreeNode().getParentTreeNode().getChildTreeNodeList();
    }

    /**
     * 构造所有节点的name
     *
     * @return
     */
    public String buildAllSelectedNames() {
        builder = new StringBuilder();
        builder.append(getName());
        buildSelectedName(this);
        return builder.toString();
    }

    /**
     * 递归拼接子节点的name
     *
     * @param TreeNode
     */
    protected void buildSelectedName(TreeNode TreeNode) {
        List<TreeNode> childTreeNodeList = TreeNode.getChildTreeNodeList();
        if (childTreeNodeList.size() > 0) {
            for (int i = 0; i < childTreeNodeList.size(); i++) {
                TreeNode childTreeNode = childTreeNodeList.get(i);
                if (childTreeNode.isSelected()) {
                    builder.append("-");
                    builder.append(childTreeNode.getName());
                    if (childTreeNode.getChildTreeNodeList().size() > 0) {
                        buildSelectedName(childTreeNode);
                    }
                }
            }
        }
    }

    protected abstract String getName();

}
