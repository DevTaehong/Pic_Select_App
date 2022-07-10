package com.example.assignment3;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Build;
import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.*;

import com.example.assignment3.databinding.FragmentItemListBinding;
import com.example.assignment3.databinding.ItemListContentBinding;

import com.example.assignment3.placeholder.PlaceholderContent;

import java.util.List;

import android.content.SharedPreferences;
import android.content.*;
import android.content.res.Resources;
import android.os.*;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import android.view.*;
import android.widget.*;
//import com.example.rogtestfabnav.databinding.*;
//import com.example.rogtestfabnav.placeholder.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.*;


/**
 * A fragment representing a list of Items. This fragment
 * has different presentations for handset and larger screen devices. On
 * handsets, the fragment presents a list of items, which when touched,
 * lead to a {@link ItemDetailFragment} representing
 * item details. On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListFragment extends Fragment {

    /**
     * Method to intercept global key events in the
     * item list fragment to trigger keyboard shortcuts
     * Currently provides a toast when Ctrl + Z and Ctrl + F
     * are triggered
     */
    private SharedPreferences preferences;
    private static final String PIC_KEY = "picList";
    private static List<String> chosenPics; //holds names of pics that have been selected



    ViewCompat.OnUnhandledKeyEventListenerCompat unhandledKeyEventListenerCompat = (v, event) -> {
        if (event.getKeyCode() == KeyEvent.KEYCODE_Z && event.isCtrlPressed()) {
            Toast.makeText(
                    v.getContext(),
                    "Undo (Ctrl + Z) shortcut triggered",
                    Toast.LENGTH_LONG
            ).show();
            return true;
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_F && event.isCtrlPressed()) {
            Toast.makeText(
                    v.getContext(),
                    "Find (Ctrl + F) shortcut triggered",
                    Toast.LENGTH_LONG
            ).show();
            return true;
        }
        return false;
    };

    private FragmentItemListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentItemListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Resources res = getResources();
        String[] pictures = res.getStringArray(R.array.pics_array);



        ViewCompat.addOnUnhandledKeyEventListener(view, unhandledKeyEventListenerCompat);
        RecyclerView recyclerView = binding.itemList;
        View itemDetailFragmentContainer = view.findViewById(R.id.item_detail_nav_container);

        View.OnClickListener onClickListener = itemView -> {
            PlaceholderContent.PlaceholderItem item =
                    (PlaceholderContent.PlaceholderItem) itemView.getTag();
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID, item.id);
            if (itemDetailFragmentContainer != null) {
                Navigation.findNavController(itemDetailFragmentContainer)
                        .navigate(R.id.fragment_item_detail, arguments);
            } else {
                Navigation.findNavController(itemView).navigate(R.id.show_item_detail, arguments);

            }
        };

        View.OnContextClickListener onContextClickListener = itemView -> {
            PlaceholderContent.PlaceholderItem item =
                    (PlaceholderContent.PlaceholderItem) itemView.getTag();
            Toast.makeText(
                    itemView.getContext(),
                    "Context click of item " + item.id,
                    Toast.LENGTH_LONG
            ).show();
            return true;
        };


        setupRecyclerView(recyclerView, onClickListener, onContextClickListener);
        loadPreferences();
        savePreferences();
        String[] names = getResources().getStringArray(R.array.pics_array);

        FloatingActionButton dbFAB = view.findViewById(R.id.dbFAB);
        dbFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent("DBActivity");//create intent object
                Bundle extras = new Bundle();//create bundle object
                extras.putString("KEY", "Stuff from bundle");//fill bundle
                i.putExtras(extras);
                startActivityForResult(i, 1);
            }
        });

    }
    private void setupRecyclerView(
            RecyclerView recyclerView,
            View.OnClickListener onClickListener,
            View.OnContextClickListener onContextClickListener
    ) {

        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(
                PlaceholderContent.ITEMS,
                onClickListener,
                onContextClickListener
        ));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


        binding = null;
    }


        public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<PlaceholderContent.PlaceholderItem> mValues;
        private final View.OnClickListener mOnClickListener;
        private final View.OnContextClickListener mOnContextClickListener;
        Integer[] imageId = {R.drawable.pic1,
                            R.drawable.pic2,
                            R.drawable.pic3,
                            R.drawable.pic4,
                            R.drawable.pic5,
                            R.drawable.pic6,
                            R.drawable.pic7};



        SimpleItemRecyclerViewAdapter(List<PlaceholderContent.PlaceholderItem> items,
                                      View.OnClickListener onClickListener,
                                      View.OnContextClickListener onContextClickListener) {
            mValues = items;
            mOnClickListener = onClickListener;
            mOnContextClickListener = onContextClickListener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            ItemListContentBinding binding =
                    ItemListContentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(binding);
        }
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).picName);
            holder.mContentImageView.setImageResource(imageId[position]);

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.itemView.setOnContextClickListener(mOnContextClickListener);
            }
            holder.itemView.setOnLongClickListener(v -> {
                // Setting the item id as the clip data so that the drop target is able to
                // identify the id of the content
                ClipData.Item clipItem = new ClipData.Item(mValues.get(position).id);
                ClipData dragData = new ClipData(
                        ((PlaceholderContent.PlaceholderItem) v.getTag()).picName,
                        new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN},
                        clipItem
                );

                if (Build.VERSION.SDK_INT >= 24) {
                    v.startDragAndDrop(
                            dragData,
                            new View.DragShadowBuilder(v),
                            null,
                            0
                    );
                } else {
                    v.startDrag(
                            dragData,
                            new View.DragShadowBuilder(v),
                            null,
                            0
                    );
                }
                return true;
            });


        }

        @Override
        public int getItemCount() {
            return mValues.size();

        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;
            final ImageView mContentImageView;

            ViewHolder(ItemListContentBinding binding) {
                super(binding.getRoot());
                mIdView = binding.idText;
                mContentView = binding.content;
                mContentImageView = binding.ivRow;
            }//end constructor

            private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PlaceholderContent.PlaceholderItem item = (PlaceholderContent.PlaceholderItem) view.getTag();
                    //Add selected item to chosenPics list and save to preferences
                    chosenPics.add(item.picName);
                }
            };//end anonymous inner class OnClickListener

        }//end named inner class ViewHolder
    }//end named inner class SimpleItemRecyclerAdapter

    public void loadPreferences() {
        if (chosenPics == null)
            chosenPics = new ArrayList<>();
        preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        String[] guards = preferences.getString(PIC_KEY, "").split(",");
        chosenPics = new ArrayList<>(Arrays.asList(guards));
    }

    public void savePreferences() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chosenPics.size(); i++) {
            sb.append(chosenPics.get(i)).append(",");
        }
        preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PIC_KEY, sb.toString());
        editor.commit();
    }


}//end class itemListFragment
