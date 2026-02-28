package org.example.prototype;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;

public class RopeComputer {
    //responsible for 1. interpreting data (assigning weights), 2. deriving states,
    // 3. planning, 4. decision-making
    ArrayList<Purchase>p_clone; //since used in multiple places, field instance makes sense
    RopeComputer(ArrayList<Purchase> p){
        p_clone=new ArrayList<>(p);
        Collections.sort(p_clone,(a,b)->a.rt.compareTo(b.rt));
    }
    public double usageTime(){
        double meaningful_amts=0;
        double max_min_dur= ChronoUnit.DAYS.between(p_clone.getFirst().rt,p_clone.getLast().rt);
        for (int i=0; i<p_clone.size()-1;i++){
            meaningful_amts+=p_clone.get(i).amt;
        }
        if (max_min_dur<=0) return p_clone.getLast().amt; //means that that much was consumed in 1 day?
        return p_clone.getLast().amt/(meaningful_amts/max_min_dur);
    }
    public double usageTime_cur_provided(double curStock){
        double meaningful_amts=0;
        double max_min_dur= ChronoUnit.DAYS.between(p_clone.getFirst().rt,p_clone.getLast().rt);
        for (int i=0; i<p_clone.size()-1;i++){
            meaningful_amts+=p_clone.get(i).amt;
        }
        if (max_min_dur<=0) return p_clone.getLast().amt;
        return curStock/(meaningful_amts/max_min_dur);
    }
    public double b_leadTime(){
        double lead_time_sum=0;
        for (int i=0;i<p_clone.size();i++){
            lead_time_sum+=ChronoUnit.DAYS.between(p_clone.get(i).bt,p_clone.get(i).rt); //kinda inversed, earlier first then later
        }
        return (lead_time_sum/p_clone.size());
    }

    public double r_leadTime(){
        double sum_tw=0;
        for (Purchase i:p_clone){
            sum_tw+=i.tw;
        }
        double avg_tw=sum_tw/p_clone.size();
        return b_leadTime() + avg_tw;
    }

    public LocalDate reorderDate(double multiplier){
        double usageTime= usageTime();
        double r_leadTime= r_leadTime();
        double f_leadTime=r_leadTime*multiplier;
        double reorder_dist=usageTime-f_leadTime;
        return p_clone.getLast().rt.plusDays((long)reorder_dist); // remember that r_dist can <0
    }
    public LocalDate reorderDate_cur_provided(double multiplier,LocalDate curDate, double curStock){
        double usageTime= usageTime_cur_provided(curStock);
        double r_leadTime= r_leadTime();
        double f_leadTime=r_leadTime*multiplier;
        double reorder_dist=usageTime-f_leadTime;
        return curDate.plusDays((long)reorder_dist);
    }
    public LocalDate reorderDate2(LocalDate d1, double totalM){
        ArrayList<Purchase> p_clone_clone=new ArrayList<>(p_clone);
        double f_leadTime=r_leadTime()*totalM;
        p_clone_clone.add(new Purchase(p_clone.getLast().itemLink,p_clone.getLast().amt,d1,d1.plusDays((long)(f_leadTime)),p_clone.getLast().tw));
        ArrayList<Purchase> temp=p_clone;
        p_clone=p_clone_clone;
        LocalDate d2=reorderDate(totalM);
        p_clone=temp;
        return d2; //default to option A again since doing it this way means no possibility of user providing curStock/curDate
    }
    public LocalDate getLatestRt(){
        return p_clone.getLast().rt;
    }
}
