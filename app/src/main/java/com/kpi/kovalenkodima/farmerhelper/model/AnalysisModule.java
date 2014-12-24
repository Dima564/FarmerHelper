package com.kpi.kovalenkodima.farmerhelper.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kovalenkodima on 12/9/14.
 */
public class AnalysisModule {

    public static void feasibilityStudy(List<Worker> workers, List<Plant> plants) {
        Map<Integer, Integer> availableQs = new HashMap<>();
        Map<Integer, Integer> requiredQs = new HashMap<>();

        for (Worker w : workers) {
            availableQs.put(w.qualificationID,availableQs.containsValue(w.qualificationID) ? availableQs.get(w.qualificationID) + 1: 0);
        }


//        for (Plant p : plants) {
//            requiredQs.put(p.technologicalMap.,availableQs.containsValue(w.qualificationID) ? availableQs.get(w.qualificationID) + 1: 0);

//        }
    }

}
