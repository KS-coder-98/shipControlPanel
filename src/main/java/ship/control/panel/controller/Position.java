package ship.control.panel.controller;

public enum Position {
    LEFTFULL{@Override public Position change(int direct){
        if(direct == 1){
            return LEFTHALF;
        }
        else
            return LEFTFULL;
    }},
    LEFTHALF{@Override public Position change(int direct){
        if(direct == 1){
            return LEFTSLOW;
        }
        else{
            return LEFTFULL;
        }

    }},
    LEFTSLOW{@Override public Position change( int direct){
        if(direct == 1){
            return STOP;
        }
        else{
            return LEFTHALF;
        }

    }},
    STOP{@Override public Position change( int direct){
        if(direct == 1){
            return RIGHTSLOW;
        }
        else{
            return LEFTSLOW;
        }
    }},
    RIGHTSLOW{@Override public Position change( int direct){
        if(direct == 1){
            return RIGHTHALF;
        }
        else{
            return STOP;
        }
    }},
    RIGHTHALF{@Override public Position change(int direct){
        if(direct == 1){
            return RIGHTFULL;
        }
        else{
            return RIGHTSLOW;
        }
    }},
    RIGHTFULL{@Override public Position change( int direct){
        if(direct == 0){
            return RIGHTHALF;
        }
        else
            return RIGHTFULL;
    }},;


    public abstract Position change(int direct);
}
