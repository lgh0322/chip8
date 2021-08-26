package com.vaca.chip8;

public class TouchData {
    float x;
    float y;
    Boolean press=false;
    public TouchData(float x, float y, Boolean press){
        this.x=x;
        this.y=y;
        this.press=press;
    }

}
