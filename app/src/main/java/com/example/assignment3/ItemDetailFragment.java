package com.example.assignment3;

import android.content.ClipData;
import android.os.Bundle;
import android.view.DragEvent;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.animation.*;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.example.assignment3.placeholder.PlaceholderContent;
import com.example.assignment3.databinding.FragmentItemDetailBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListFragment}
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The placeholder content this fragment is presenting.
     */
    private PlaceholderContent.PlaceholderItem mItem;
    private CollapsingToolbarLayout mToolbarLayout;
    private TextView mTextView;

    private final View.OnDragListener dragListener = (v, event) -> {
        if (event.getAction() == DragEvent.ACTION_DROP) {
            ClipData.Item clipDataItem = event.getClipData().getItemAt(0);
            mItem = PlaceholderContent.ITEM_MAP.get(clipDataItem.getText().toString());
        }
        return true;
    };
    private FragmentItemDetailBinding binding;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the placeholder content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = PlaceholderContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }
    private Animation myAnimation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentItemDetailBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        mToolbarLayout = rootView.findViewById(R.id.toolbar_layout);
        mTextView = binding.itemDetail;

        myAnimation = AnimationUtils.loadAnimation(getContext(),R.anim.wave);

        // Show the placeholder content as text in a TextView & in the toolbar if available.
        if (mItem != null){
            switch (mItem.picName){
                case "Moon":
                    ((ImageView)rootView.findViewById(R.id.item_detail_image)).setImageResource(R.drawable.pic1);
                    ((ImageView)rootView.findViewById(R.id.item_detail_image)).startAnimation(myAnimation);
                    break;
                case "Tram":
                    ((ImageView)rootView.findViewById(R.id.item_detail_image)).setImageResource(R.drawable.pic2);
                    ((ImageView)rootView.findViewById(R.id.item_detail_image)).startAnimation(myAnimation);
                    break;
                case "Bridge":
                    ((ImageView)rootView.findViewById(R.id.item_detail_image)).setImageResource(R.drawable.pic3);
                    ((ImageView)rootView.findViewById(R.id.item_detail_image)).startAnimation(myAnimation);
                    break;
                case "City":
                    ((ImageView)rootView.findViewById(R.id.item_detail_image)).setImageResource(R.drawable.pic4);
                    ((ImageView)rootView.findViewById(R.id.item_detail_image)).startAnimation(myAnimation);
                    break;
                case "River":
                    ((ImageView)rootView.findViewById(R.id.item_detail_image)).setImageResource(R.drawable.pic5);
                    ((ImageView)rootView.findViewById(R.id.item_detail_image)).startAnimation(myAnimation);
                    break;
                case "Sunset":
                    ((ImageView)rootView.findViewById(R.id.item_detail_image)).setImageResource(R.drawable.pic6);
                    ((ImageView)rootView.findViewById(R.id.item_detail_image)).startAnimation(myAnimation);
                    break;
                case "Bridge2":
                    ((ImageView)rootView.findViewById(R.id.item_detail_image)).setImageResource(R.drawable.pic7);
                    ((ImageView)rootView.findViewById(R.id.item_detail_image)).startAnimation(myAnimation);
                    break;
                default:
                    ((ImageView)rootView.findViewById(R.id.item_detail_image)).setImageResource(R.drawable.pic1);
                    ((ImageView)rootView.findViewById(R.id.item_detail_image)).startAnimation(myAnimation);
            }

        }
        rootView.setOnDragListener(dragListener);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}