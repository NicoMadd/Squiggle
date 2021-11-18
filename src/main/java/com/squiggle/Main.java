package com.squiggle;

// import com.squiggle.queries.SelectQuery;
// import src.main.java.com.squiggle.*;

import com.squiggle.queries.SelectQuery;

public class Main {
    public static void main(String[] args) {
        SelectQuery select = Squiggle.Select().from("table").select("column");
        System.out.println(select.toString(true));
    }
}
