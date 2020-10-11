package com.example.plauction.Common;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.plauction.Constants.Constants;
import com.example.plauction.Entities.AuctionTeamsEntity;
import com.example.plauction.Entities.ElementsEntity;
import com.example.plauction.Entities.HistoryEntity;
import com.example.plauction.Entities.PlayerInfoEntity;
import com.example.plauction.Entities.TransferEntity;
import com.example.plauction.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CommonFunctions {

    private  static Map<Integer, ElementsEntity> playerIdToElementMap_;
    private  static Map<String, List<HistoryEntity>> elementSummaries;


    public static Map<String, List<HistoryEntity>> getElementSummaries() {
        return elementSummaries;
    }

    public static void setElementSummaries(Map<String, List<HistoryEntity>> elementSummaries) {
        CommonFunctions.elementSummaries = elementSummaries;
    }
    // General toasts ..............

    public static void makeToast(String message, Activity activity) {
        if(activity!=null){
            LayoutInflater inflater = LayoutInflater.from(activity.getApplicationContext());
            View toastLayout = inflater.inflate(R.layout.custom_toast, null);
            TextView toastMessage = (TextView) toastLayout.findViewById(R.id.toastMessage);
            toastMessage.setText(message);
            Toast toast = new Toast(activity.getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(toastLayout);

            toast.setGravity(Gravity.BOTTOM,0,0);
            toast.show();
        }
    }


    public static void makeToast(String message, Context context) {
        if(context !=null){
            LayoutInflater inflater = LayoutInflater.from(context);
            View toastLayout = inflater.inflate(R.layout.custom_toast, null);
            TextView toastMessage = (TextView) toastLayout.findViewById(R.id.toastMessage);
            toastMessage.setText(message);
            Toast toast = new Toast(context);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(toastLayout);

            toast.setGravity(Gravity.TOP,0,0);
            toast.show();
        }
    }

    // Snack bar - message only

    public static  Snackbar makeSnackBar(String message, View view) {
        int duration = 1000;
        Snackbar snackbar = Snackbar.make(view, message, duration);
        View v = snackbar.getView();
        TextView tv =v.findViewById(com.google.android.material.R.id.snackbar_text);
        tv.setTypeface(Constants.monte);
        return snackbar;
    }

    public static int isEmpty(EditText e){
        String text = e.getText().toString();
        if(text.length()==0){
            return 1;
        }
        else
            return 0;
    }


    public static String getErrorMessage(int code, Context context) {
        String message;
        if(code == 401) {
            message = context.getString(R.string.unauthorized);
        }
        else if (code == 400) {
            message = context.getString(R.string.invalidData);
        }
        else if (code == 500 ) {
            message = context.getString(R.string.unexpectedError);
        }
        else if(code == 700) {
            message = context.getString(R.string.noConnection);
        }
        else
        {
            message = context.getString(R.string.unexpectedError);
        }

        return message;
    }

    public static Map<Integer, ElementsEntity> getPlayerIdToElementMap_() {
        return playerIdToElementMap_;
    }

    public static void setPlayerIdToElementMap_(Map<Integer, ElementsEntity> playerIdToElementMap_) {
        CommonFunctions.playerIdToElementMap_ = playerIdToElementMap_;
    }


    public static int getTeamTotalSum(ArrayList<PlayerInfoEntity> playerinfoArrayList)
    {
        if(playerIdToElementMap_ == null || playerIdToElementMap_.size() == 0)
            return 0;

        int sum =0;
        for(PlayerInfoEntity player: playerinfoArrayList)
        {
            if(!playerIdToElementMap_.containsKey(player.getPlayerId()))
            {
                sum+=0;
            }
            else
            {
                sum+=playerIdToElementMap_.get(player.getPlayerId()).getTotal_points();
            }
        }
        return sum;
    }

    public static int getGameWeekAggSum(ArrayList<PlayerInfoEntity> playerinfoArrayList)
    {
        if(elementSummaries == null || elementSummaries.size() == 0)
            return 0;

        //SINCE I UNDERSTAND IN AND OUT DENOTE THE GW RANGE and this needs to be present within the PlayerInfoEntity
        int sum =0;
        for(PlayerInfoEntity player: playerinfoArrayList)
        {
            if(!playerIdToElementMap_.containsKey(player.getPlayerId()))
            {
                sum+=0;
            }
            else
            {
                //gameweek sum logic
                List<HistoryEntity> gwHistory=elementSummaries.get(player.getPlayerId()+"");
                if(gwHistory == null)
                {
                    sum+=0;
                    continue;
                }
                TransferEntity transfer=player.getTransfers();
                if(transfer!=null){
                    // decide start and end
                    int start = transfer.getIn() == 0 ? 0 : transfer.getIn() - 1;
                    int end = transfer.getOut() == 0 ? gwHistory.size() - 1  : transfer.getOut() - 1;
                    for(int i=start;i<=end;i++){
                        sum+=gwHistory.get(i).getTotal_points();
                    }
                }
                //If no transfer exists for a player simply adding the total points and moving onto the next player
                else{
                    sum+=playerIdToElementMap_.get(player.getPlayerId()).getTotal_points();
                }
            }
        }
        return sum;
    }


    public static AuctionTeamsEntity filterPlayers(AuctionTeamsEntity auctionTeamsEntity, int elementType)
    {
        ArrayList<PlayerInfoEntity> playerinfos = auctionTeamsEntity.getPlayerInfo();
        ArrayList<PlayerInfoEntity> filteredPlayerInfos= new ArrayList<>();

        for(PlayerInfoEntity p: playerinfos){
            if(playerIdToElementMap_.containsKey(p.getPlayerId()) && playerIdToElementMap_.get(p.getPlayerId()).getElement_type() == elementType)
                filteredPlayerInfos.add(p);
        }

        return new AuctionTeamsEntity(auctionTeamsEntity.getTeamName(), filteredPlayerInfos,null);

    }


    public static void populatePieChart(PieChart chart, List<PieEntry> entries)
    {
        chart.setUsePercentValues(false);

        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);

        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);
        chart.animateY(1400, Easing.EaseInOutQuad);

        PieDataSet dataSet = new PieDataSet(entries,"");

        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        PieData data = new PieData(dataSet);
        dataSet.setValueTextSize(16);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        chart.setData(data);
        chart.setDrawEntryLabels(false);
        chart.highlightValues(null);
        chart.getDescription().setEnabled(false);
    }


    public static AlertDialog getTeamComposition(final AuctionTeamsEntity auctionTeamsEntity, final Context context, int composition){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View dialogView = layoutInflater.inflate(R.layout.alert_teamcomp, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(dialogView);

        TextView rank = (TextView)dialogView.findViewById(R.id.temcomp_rank);
        TextView title =(TextView)dialogView.findViewById(R.id.diaglog_title);
        title.setText(auctionTeamsEntity.getTeamName());


        PieChart chart = (PieChart)dialogView.findViewById(R.id.pie_chart);

        AuctionTeamsEntity fwds = filterPlayers(auctionTeamsEntity,4);
        AuctionTeamsEntity mids = filterPlayers(auctionTeamsEntity,3);
        AuctionTeamsEntity defs = filterPlayers(auctionTeamsEntity,2);
        AuctionTeamsEntity gks  = filterPlayers(auctionTeamsEntity,1);

        List<PieEntry> teamCompositionEntities = new ArrayList<>();
        teamCompositionEntities.add(new PieEntry(getGameWeekAggSum(fwds.getPlayerInfo()),"FORWARDS"));
        teamCompositionEntities.add(new PieEntry(getGameWeekAggSum(mids.getPlayerInfo()),"MIDFIELDERS"));
        teamCompositionEntities.add(new PieEntry(getGameWeekAggSum(defs.getPlayerInfo()),"DEFENDERS"));
        teamCompositionEntities.add(new PieEntry(getGameWeekAggSum(gks.getPlayerInfo()),"KEEPERS"));
        populatePieChart(chart, teamCompositionEntities);

        chart.postInvalidate();

        //TeamCompositionAdapter teamCompositionAdapter = new TeamCompositionAdapter(context,teamCompositionEntities);

        Button confirm = dialogView.findViewById(R.id.confirm_reg);

        rank.setText("RANK - " + composition + ", POINTS - " + getTeamTotalSum(auctionTeamsEntity.getPlayerInfo()));

        alertDialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        return alertDialog;
    }

}
