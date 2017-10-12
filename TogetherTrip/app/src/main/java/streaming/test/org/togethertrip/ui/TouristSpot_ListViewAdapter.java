package streaming.test.org.togethertrip.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import streaming.test.org.togethertrip.R;
import streaming.test.org.togethertrip.datas.DetailWithTour;
import streaming.test.org.togethertrip.datas.TouristSpotSearchList;

/**
 * Created by taehyung on 2017-09-05.
 */

//ListView사용을 위한 어댑터
public class TouristSpot_ListViewAdapter extends BaseAdapter implements Filterable {
    final static String TAG = "ListViewAdapterLog";
    ArrayList<TouristSpotSearchList> touristSpotSearchResultList;
    Context context;

    String contentId;
    String contentTypeId;

    String parking;
    String route;
    String wheelchair;
    String elevator;
    String restroom;
    String handicapEtc;
    String braileblock;

    String addr;

    Filter listFilter;
    ArrayList<TouristSpotSearchList> filteredItemList;

    ImageButton filter_wheelchairs, filter_bathroom, filter_parkinglot, filter_elevator;
    DetailWithTour detailWithTour;


    public TouristSpot_ListViewAdapter(Context context){
        this.context = context;
    }

    public TouristSpot_ListViewAdapter(Context context ,ArrayList<TouristSpotSearchList> touristSpotSearchResultList){
        this.context = context;
        this.touristSpotSearchResultList = touristSpotSearchResultList;
    }

    @Override
    public int getCount() {
        if(touristSpotSearchResultList == null){
            return 0;
        }else {
            return touristSpotSearchResultList.size();
        }
    }

    @Override
    public TouristSpotSearchList getItem(int position) {
        return touristSpotSearchResultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.tourist_spot_list_view_item,null);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView iv_bigImg = (ImageView) convertView.findViewById(R.id.iv_bigImg);
        ImageButton ib_bigImgHeart = (ImageButton) convertView.findViewById(R.id.ib_bigImgHeart);
        ImageView iv_profileImg = (ImageView) convertView.findViewById(R.id.iv_profileImg);
        TextView tv_spotName = (TextView) convertView.findViewById(R.id.tv_spotName);
        TextView tv_spotAddr = (TextView) convertView.findViewById(R.id.tv_spotAddr);
        TextView tv_heartCount = (TextView) convertView.findViewById(R.id.tv_heartCount);
        TextView tv_commentCount = (TextView)convertView.findViewById(R.id.tv_commentCount);

        filter_bathroom = (ImageButton) convertView.findViewById(R.id.filter_bathroom);
        filter_elevator = (ImageButton) convertView.findViewById(R.id.filter_elevator);
        filter_parkinglot = (ImageButton) convertView.findViewById(R.id.filter_parkinglot);
        filter_wheelchairs = (ImageButton) convertView.findViewById(R.id.filter_wheelchairs);

        // 아이템 내 각 위젯에 데이터 반영
//        ib_bigImgHeart.setImageDrawable(touristSpotListView.Tripinfo.); // 하트버튼 스와이프 구현해야함
       Glide.with(context).load(touristSpotSearchResultList.get(position).tripinfo.firstimage)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(iv_bigImg);
        tv_spotAddr.setText(touristSpotSearchResultList.get(position).tripinfo.addr1);
        tv_spotName.setText(touristSpotSearchResultList.get(position).tripinfo.title);
        contentId = touristSpotSearchResultList.get(position).tripinfo.contentid;
        contentTypeId = touristSpotSearchResultList.get(position).tripinfo.contenttypeid;

        parking = touristSpotSearchResultList.get(position).detailWithTour.parking;
        route = touristSpotSearchResultList.get(position).detailWithTour.route;
        wheelchair = touristSpotSearchResultList.get(position).detailWithTour.wheelchair;
        elevator = touristSpotSearchResultList.get(position).detailWithTour.elevator;
        restroom = touristSpotSearchResultList.get(position).detailWithTour.restroom;
        handicapEtc = touristSpotSearchResultList.get(position).detailWithTour.handicapetc;
        braileblock = touristSpotSearchResultList.get(position).detailWithTour.braileblock;
        tv_heartCount.setText(String.valueOf(touristSpotSearchResultList.get(position).tripinfo.likecount));
        tv_commentCount.setText(String.valueOf(touristSpotSearchResultList.get(position).tripinfo.commentcount));

        //시설 정보 유무에 따른 이미지
        checkFacilities();

        //상세보기에서 주소 보여주기 위함
        addr = touristSpotSearchResultList.get(position).tripinfo.addr1;

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    /*
    * TODO 필터링...
     */
    @Override
    public Filter getFilter() {
        if (listFilter == null) {
            listFilter = new ListFilter() ;
        } return listFilter ;

    }

    private class ListFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if(constraint == null || constraint.length() == 0){
                results.values = touristSpotSearchResultList;
                results.count = touristSpotSearchResultList.size();
            }else{
                ArrayList<TouristSpotSearchList> itemList = new ArrayList<TouristSpotSearchList>();
                for(TouristSpotSearchList item : touristSpotSearchResultList){
                    /*
                    TODO 버튼이 눌림을 확인하고 item 추가
                    http://recipes4dev.tistory.com/96

                     */



                }

                results.values = itemList;
                results.count = itemList.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredItemList = (ArrayList<TouristSpotSearchList>) results.values;

            if(results.count>0){
                notifyDataSetChanged();
            }else{
                notifyDataSetInvalidated();
            }
        }
    }

    public void checkFacilities(){
        try {
            if (!elevator.equals(null)) {
                filter_elevator.setBackgroundResource(R.drawable.trips_facilityfilter_elevator_on);
            }
        }catch (NullPointerException ne){
            filter_elevator.setBackgroundResource(R.drawable.trips_facilityfilter_elevator_off);
        }
        try{
            if(!parking.equals(null)) {
                filter_parkinglot.setBackgroundResource(R.drawable.trips_facilityfilter_parkinglot_on);
            }
        }catch (NullPointerException ne){
            filter_parkinglot.setBackgroundResource(R.drawable.trips_facilityfilter_parkinglot_off);
        }
        try {
            if (!restroom.equals(null)) {
                filter_bathroom.setBackgroundResource(R.drawable.trips_facilityfilter_bathroom_on);
            }
        }catch (NullPointerException ne){
            filter_bathroom.setBackgroundResource(R.drawable.trips_facilityfilter_bathroom_off);
        }
        try{
            if(!wheelchair.equals(null)) {
                filter_wheelchairs.setBackgroundResource(R.drawable.trips_facilityfilter_wheelchairs_on);
            }
        }catch (NullPointerException ne){
            filter_wheelchairs.setBackgroundResource(R.drawable.trips_facilityfilter_wheelchairs_off);
        }
    }


}
