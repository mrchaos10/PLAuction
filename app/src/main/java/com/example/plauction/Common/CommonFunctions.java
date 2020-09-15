package com.example.plauction.Common;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plauction.Adapters.TeamCompositionAdapter;
import com.example.plauction.Constants.Constants;
import com.example.plauction.Entities.AuctionTeamsEntity;
import com.example.plauction.Entities.ElementsEntity;
import com.example.plauction.Entities.HistoryEntity;
import com.example.plauction.Entities.PlayerInfoEntity;
import com.example.plauction.Entities.TeamCompositionEntity;
import com.example.plauction.Entities.TransferEntity;
import com.example.plauction.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CommonFunctions {

    private  static Map<Integer, ElementsEntity> playerIdToElementMap_;
    private static Map<Integer,List<HistoryEntity>> playerIdtoHistoryMap_;
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
        if(playerIdtoHistoryMap_ == null || playerIdtoHistoryMap_.size() == 0)
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
                //gameweek sum logic
                List<HistoryEntity> gwHistory=playerIdtoHistoryMap_.get(player.getPlayerId());
                TransferEntity transfer=player.getTransferEntity();
                for(int i=transfer.getIn();i<transfer.getOut();i++){
                    sum+=gwHistory.get(i).getTotal_points();
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


    public static AlertDialog getTeamComposition(final AuctionTeamsEntity auctionTeamsEntity, final Context context, int composition){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View dialogView = layoutInflater.inflate(R.layout.alert_teamcomp, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(dialogView);

        TextView rank = (TextView)dialogView.findViewById(R.id.temcomp_rank);
        TextView title =(TextView)dialogView.findViewById(R.id.diaglog_title);
        title.setText(auctionTeamsEntity.getTeamName());
        GridView gridView = (GridView)dialogView.findViewById(R.id.temcomp_grid);

        AuctionTeamsEntity fwds = filterPlayers(auctionTeamsEntity,4);
        AuctionTeamsEntity mids = filterPlayers(auctionTeamsEntity,3);
        AuctionTeamsEntity defs = filterPlayers(auctionTeamsEntity,2);
        AuctionTeamsEntity gks  = filterPlayers(auctionTeamsEntity,1);

        ArrayList<TeamCompositionEntity> teamCompositionEntities = new ArrayList<>();

        TeamCompositionEntity fwdEntity = new TeamCompositionEntity("FORWARDS", getTeamTotalSum(fwds.getPlayerInfo()));
        teamCompositionEntities.add(fwdEntity);
        TeamCompositionEntity midsEntity = new TeamCompositionEntity("MIDFIELDERS", getTeamTotalSum(mids.getPlayerInfo()));
        teamCompositionEntities.add(midsEntity);
        TeamCompositionEntity defsEntity = new TeamCompositionEntity("DEFENDERS", getTeamTotalSum(defs.getPlayerInfo()));
        teamCompositionEntities.add(defsEntity);
        TeamCompositionEntity gksEntity = new TeamCompositionEntity("KEEPERS", getTeamTotalSum(gks.getPlayerInfo()));
        teamCompositionEntities.add(gksEntity);

        TeamCompositionAdapter teamCompositionAdapter = new TeamCompositionAdapter(context,teamCompositionEntities);
        gridView.setAdapter(teamCompositionAdapter);

        Button confirm = dialogView.findViewById(R.id.confirm_reg);

        rank.setText("RANK - " + composition + ", POINTS - " + getTeamTotalSum(auctionTeamsEntity.getPlayerInfo()));

        final AlertDialog alertDialog = alertDialogBuilder.create();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        return alertDialog;
    }

    public static Map<Integer, List<HistoryEntity>> getPlayerIdtoHistoryMap_() {
        return playerIdtoHistoryMap_;
    }

    public static void setPlayerIdtoHistoryMap_(Map<Integer, List<HistoryEntity>> playerIdtoHistoryMap_) {
        CommonFunctions.playerIdtoHistoryMap_ = playerIdtoHistoryMap_;
    }
}
