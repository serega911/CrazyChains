package com.example.crazychains;


public class CC_Cell {
    private CC_I_CellView view;
    private Integer num;
    private Integer color;

    public CC_Cell(CC_I_CellView view, Integer num, Integer color) {
        this.view = view;
        this.num = num;
        this.color = color;
        update();
    }

    private void update() {
        view.setColor(color);
        view.setNum(num);
    }

    public boolean check(CC_Cell obj) {
        return ((num.intValue() == obj.num.intValue()) || (color.intValue() == obj.color.intValue()));
    }

    public boolean checkIsNext(CC_Cell obj) {
        return num - obj.num == 1 && (color.intValue() == obj.color.intValue());
    }

    public CC_I_CellView getView() {
        return view;
    }

    public void swap(CC_Cell obj) {
        Integer num = this.num;
        Integer color = this.color;
        this.num = obj.num;
        this.color = obj.color;
        obj.num = num;
        obj.color = color;

        obj.update();
        this.update();
    }
}
