package com.olegel.professor.constants.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.olegel.professor.constants.ElectrostaticPermeability;
import com.olegel.professor.constants.MainActivity;
import com.olegel.professor.constants.R;

import java.util.ArrayList;

/**
 * Created by Professor on 13.12.2016.
 */

public class FriendListAdapter extends BaseAdapter implements Filterable {
    public static final String TAG = FriendListAdapter.class.getSimpleName();
    private MainActivity activity;
    private FriendFilter friendFilter;
    private Typeface typeface;
    private ArrayList<ElectrostaticPermeability> friendList;
    private ArrayList<ElectrostaticPermeability> filteredList;
    public FriendListAdapter(MainActivity activity, ArrayList<ElectrostaticPermeability> friendList) {
        this.activity = activity;
        this.friendList = friendList;
        this.filteredList = friendList;
       // typeface = Typeface.createFromAsset(activity.getAssets(), null);

        getFilter();
    }

    /**
     * Get size of user list
     * @return userList size
     */
    @Override
    public int getCount() {
        return filteredList.size();
    }

    /**
     * Get specific item from user list
     * @param i item index
     * @return list item
     */
    @Override
    public Object getItem(int i) {
        return filteredList.get(i);
    }

    /**
     * Get user list item id
     * @param i item index
     * @return current item id
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * Create list row view
     * @param position index
     * @param view current list item view
     * @param parent parent
     * @return view
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // A ViewHolder keeps references to children views to avoid unnecessary calls
        // to findViewById() on each row.
        final ViewHolder holder;
        final ElectrostaticPermeability user = (ElectrostaticPermeability) getItem(position);

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.drawer_spinner, parent, false);
            holder = new ViewHolder();
            //holder.iconText = (TextView) view.findViewById(R.id.icon_text);
            holder.name = (TextView) view.findViewById(R.id.spinner_text);
          //  holder.iconText.setTypeface(typeface, Typeface.BOLD);
            holder.iconText.setTextColor(activity.getResources().getColor(R.color.colorAccent));
         //   holder.name.setTypeface(typeface, Typeface.NORMAL);

            view.setTag(holder);
        } else {
            // get view holder back
            holder = (ViewHolder) view.getTag();
        }

        // bind text with view holder content view for efficient use
       // holder.iconText.setText("#");
        holder.name.setText(user.getSubstance());
       // view.setBackgroundResource(R.drawable.friend_list_selector);

        return view;
    }

    /**
     * Get custom filter
     * @return filter
     */
    @Override
    public Filter getFilter() {
        if (friendFilter == null) {
            friendFilter = new FriendFilter();
        }

        return friendFilter;
    }

    /**
     * Keep reference to children view to avoid unnecessary calls
     */
    static class ViewHolder {
        TextView iconText;
        TextView name;
    }

    /**
     * Custom filter for friend list
     * Filter content in friend list according to the search text
     */
    private class FriendFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<ElectrostaticPermeability> tempList = new ArrayList<ElectrostaticPermeability>();

                // search content in friend list
                for (ElectrostaticPermeability user : friendList) {
                    if (user.getSubstance().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(user);
                        Log.d(TAG, "performFiltering: "+user.getSubstance());
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = friendList.size();
                filterResults.values = friendList;
            }

            return filterResults;
        }

        /**
         * Notify about filtered list to ui
         * @param constraint text
         * @param results filtered result
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (ArrayList<ElectrostaticPermeability>) results.values;
            notifyDataSetChanged();
        }
    }
}
