package com.eLearnPlayer.entity;

/**
 * 包含 level 和 selectedRank 属性的实体类
 * 用于接受用户的水平和选择的关卡号
 * @author
 * @create 2024-04-15 23:22
 */
public class SelectedRequest {
    private int level;
    private int selectedRank;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSelectedRank() {
        return selectedRank;
    }

    public void setSelectedRank(int selectedRank) {
        this.selectedRank = selectedRank;
    }
}
