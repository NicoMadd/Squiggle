package com.squiggle;

import com.squiggle.queries.*;

public class Squiggle {

    public static SelectQuery Select() {
        return new SelectQuery();
    }

    public static DeleteQuery Delete() {
        return new DeleteQuery();
    }

    public static UpdateQuery Update() {
        return new UpdateQuery();
    }

    public static InsertQuery Insert() {
        return new InsertQuery();
    }
}
