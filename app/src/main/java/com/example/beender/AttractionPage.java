package com.example.beender;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.beender.model.ItemAdditionalData;
import com.example.beender.model.ItemModel;
import com.example.beender.model.Review;

import java.util.List;

public class AttractionPage extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    ItemModel attractionArg;

    public AttractionPage() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment AttractionPage.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static AttractionPage newInstance(String param1, String param2) {
//        AttractionPage fragment = new AttractionPage();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }

    public AttractionPage(ItemModel attractionArg) {
        this.attractionArg = attractionArg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            attractionArg = (ItemModel) getArguments().getSerializable("attraction");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attraction_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (attractionArg == null) {
            dismiss();
            return;
        }

        attractionArg.setThumbnailLoadedListener(() -> {
            new Handler(Looper.getMainLooper()).post(() -> {
                setThumbnails(view, attractionArg.fetchAdditionalData().getImages());
            });
        });

        initializeViews(view);

        ((MainActivity)getActivity()).getLoadingDialog().dismiss();

    }

    private void setUpStars(View parent) {
        LinearLayout starsLayout = (LinearLayout)parent.findViewById(R.id.stars_layout);

        double attractionRating = attractionArg.getRatingAsDouble();
        double roundedAttractionRating = Math.ceil(attractionRating * 2) / 2.0;
        for (int i = 0; i < roundedAttractionRating; i++) {
            ImageView star = new ImageView(getContext());
            star.setColorFilter(new PorterDuffColorFilter(Color.parseColor("#FFFFC107"), PorterDuff.Mode.SRC_IN));
            int sizeInDp = 30;
            int sizeInPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeInDp, getResources().getDisplayMetrics());
            star.setLayoutParams(new ViewGroup.LayoutParams(sizeInPx, sizeInPx));

            if (roundedAttractionRating - i >= 1) {
                star.setImageResource(R.drawable.star_filled);
            } else {
                star.setImageResource(R.drawable.star_half);
            }


            starsLayout.addView(star);
        }
    }

    private void setUpReviews(View parent) {
        ItemAdditionalData additionalData = attractionArg.fetchAdditionalData();

        List<Review> reviews = additionalData.getReviews();
        LinearLayout reviewsLayout = (LinearLayout)parent.findViewById(R.id.reviews_layout);

        if (reviews == null || reviews.size() == 0) {
            parent.findViewById(R.id.reviews_title).setVisibility(View.GONE);
            return;
        }

        for (Review review : reviews) {

            ImageView profilePicture = new ImageView(getContext());
            profilePicture.setImageBitmap(review.getProfilePicture());


            // create a SpannableStringBuilder and append the image, author name and rating
            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append(new SpannableString(" "));

            // Append the author name in bold
            String authorName = review.getAuthorName() + " ";
            SpannableStringBuilder authorBuilder = new SpannableStringBuilder(authorName);
            authorBuilder.setSpan(new RelativeSizeSpan(1.2f), 0, authorName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            authorBuilder.setSpan(new StyleSpan(Typeface.BOLD), 0, authorName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            SpannableStringBuilder starsAndDateBuilder = new SpannableStringBuilder();

            // Append the rating stars
            int rating = review.getRating();
            String stars = " ";
            for (int i = 0; i < rating; i++) {
                stars += "★";
            }
            stars += "  ";


            ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#FFFFC107"));
            starsAndDateBuilder.append(new SpannableString(stars), span, 0);


            String relativeTimeDescription = review.getRelativeTimeDescription() ;
            starsAndDateBuilder.append(relativeTimeDescription);

            // Append the review text

            builder.append(authorBuilder);
            builder.append(starsAndDateBuilder);

            // Create a TextView and set the text
            TextView reviewHeadTextView = new TextView(getContext());
            reviewHeadTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            reviewHeadTextView.setPadding(10, 10, 10, 20);
            reviewHeadTextView.setTextSize(14);
            reviewHeadTextView.setText(builder);

            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setGravity(Gravity.CENTER_VERTICAL);
            linearLayout.addView(profilePicture);
            linearLayout.addView(reviewHeadTextView);

            reviewsLayout.addView(linearLayout);

            TextView reviewTextView = new TextView(getContext());
            reviewTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            reviewTextView.setPadding(10, 10, 10, 50);
            reviewTextView.setTextSize(14);
            reviewTextView.setText(review.getText());

            reviewsLayout.addView(reviewTextView);
        }
    }

    private void setUpUrl(View parent) {
        TextView urlView = parent.findViewById(R.id.attraction_url);
        String url = attractionArg.fetchAdditionalData().getWebsite();

        if (url == null){
            urlView.setVisibility(View.GONE);
            return;
        }
//        urlView.setText(url);
        urlView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the Wikipedia page for the attraction
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

    }

    private void setMainImage(View parent, List<Bitmap> images, int currentPosition) {
        Bitmap currentImage = images.get(currentPosition);
        ImageView mainImage = (ImageView)parent.findViewById(R.id.main_image);
        mainImage.setImageBitmap(currentImage);

        if (currentImage != null) {
            mainImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageFragment dialog = new ImageFragment(currentImage);
                    dialog.show(requireActivity().getSupportFragmentManager(), "ImageFragment");
                }
            });
        }
    }

    private void setAllImages(View parent, List<Bitmap> images) {
        setMainImage(parent, images, 0);
        setThumbnails(parent, images);
    }

    private void setThumbnails(View parent, List<Bitmap> images) {
        try {
            LinearLayout thumbnailLayout = parent.findViewById(R.id.thumbnail_layout);
            thumbnailLayout.removeAllViews();

            int width = getResources().getDisplayMetrics().widthPixels / 3; // get 1/3rd of the screen width
            int height = (int) (width * 0.5); // set the height proportional to the width

            int padding = 4; // in dp
            float scale = getResources().getDisplayMetrics().density;
            int pixelPadding = (int) (padding * scale + 0.5f);

            for (int i = 0; i < images.size(); i++) {
                addSingleThumbail(parent, thumbnailLayout, width, height, pixelPadding, images, i);
            }

            if (!attractionArg.isDoneLoadingImages()) {
                addSingleThumbail(parent, thumbnailLayout, width, height, pixelPadding, images, -1);
            }
        } catch (IllegalStateException e) {
            if (getContext() == null) {
                // This is fine - means the page was closed before loading was complete.
            } else {
                e.printStackTrace();
            }
        }
    }

    private void addSingleThumbail(View parent,  LinearLayout thumbnailLayout, int width, int height, int pixelPadding, List<Bitmap> images, final int index) {
        boolean isRealImage = index >= 0;



        if (isRealImage) {
            ImageView imageView = new ImageView(getContext());
            imageView.setLayoutParams(new LinearLayout.LayoutParams(width, height));
            imageView.setPadding(pixelPadding, 0, pixelPadding, 0);

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Bitmap image = images.get(index);
            imageView.setImageBitmap(image);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setMainImage(parent, images, index);
                }
            });

            thumbnailLayout.addView(imageView);

        } else {
            ProgressBar progressBar = new ProgressBar(getContext());
            progressBar.setLayoutParams(new LinearLayout.LayoutParams(width, height));
            progressBar.setPadding(pixelPadding, 0, pixelPadding, 0);
//            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//            Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.loading);
//
//            int color = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
//            if (color == Configuration.UI_MODE_NIGHT_YES) {
//                imageView.setColorFilter(Color.WHITE);
//            } else {
//                imageView.setColorFilter(Color.BLACK);
//            }
//
//            imageView.setImageBitmap(image);

            thumbnailLayout.addView(progressBar);


        }

    }

    private void initializeViews(View parent) {
        TextView attractionTitle = (TextView)parent.findViewById(R.id.attraction_title);
        attractionTitle.setText(attractionArg.getName());
        ImageView attractionImage = (ImageView)parent.findViewById(R.id.main_image);

        List<Bitmap> images = attractionArg.fetchAdditionalData().getImages();

        if (images.size() == 0) {
            attractionImage.setImageBitmap(attractionArg.getImage());
        } else {
            setAllImages(parent, images);
        }


        setUpStars(parent);
        setUpReviews(parent);
        setUpUrl(parent);

        TextView attractionDescription = (TextView)parent.findViewById(R.id.attraction_description);
        TextView readMore = (TextView)parent.findViewById(R.id.read_more);

        String fullDescription = attractionArg.fetchAdditionalData().getDescription().trim();

        if (fullDescription.isEmpty()) {
            attractionDescription.setVisibility(View.GONE);
            parent.findViewById(R.id.description_title).setVisibility(View.GONE);
        }

        int index = fullDescription.indexOf("\n"); // Find the index of the first double line break
        String description;
        if (index >= 0) {
            description = fullDescription.substring(0, index); // Get the text before the first line break

            readMore.setVisibility(View.VISIBLE);
            readMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // uncomment this to redirect to wikipedia on click
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/" + attractionArg.getName()));
//                    startActivity(intent);

                    attractionDescription.setText(attractionArg.fetchAdditionalData().getDescription());
                    readMore.setVisibility(View.GONE);

                }
            });
        } else {
            readMore.setVisibility(View.INVISIBLE);
            description = fullDescription; // There are no double line breaks, so use the entire text as the first paragraph
        }

        attractionDescription.setText(description);




    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();

            params.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.95);

            params.height = (int) (getResources().getDisplayMetrics().heightPixels * 0.95);

            dialog.getWindow().setAttributes(params);

            Window window = dialog.getWindow();

            if (window != null) {
                window.setGravity(Gravity.CENTER);

                // create a drawable with rounded corners
                GradientDrawable drawable = new GradientDrawable();

                TypedValue typedValue = new TypedValue();
                Resources.Theme theme = getActivity().getTheme();
                theme.resolveAttribute(com.google.android.material.R.attr.colorOnPrimary, typedValue, true);
                int color = typedValue.data;

                drawable.setColor(color);
                drawable.setCornerRadius(20);
                window.setBackgroundDrawable(drawable);
            }
        }


    }
}