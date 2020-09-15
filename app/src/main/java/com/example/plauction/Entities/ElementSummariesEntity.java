package com.example.plauction.Entities;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElementSummariesEntity {
    public interface RestClientInterface{
        void onResponseLoaded(int code, VolleyError error, Map<String, List<HistoryEntity>> elementSummaryEntity);
    }
}
