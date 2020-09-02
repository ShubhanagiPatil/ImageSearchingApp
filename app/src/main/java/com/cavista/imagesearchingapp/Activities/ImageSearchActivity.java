package com.cavista.imagesearchingapp.Activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cavista.imagesearchingapp.Adapters.ImagesAdapter;
import com.cavista.imagesearchingapp.Interface.ImageISearchnterface;
import com.cavista.imagesearchingapp.Model.ImageData;
import com.cavista.imagesearchingapp.Model.ImageJsonObject;
import com.cavista.imagesearchingapp.Model.SearchImages;
import com.cavista.imagesearchingapp.Network.ApiClient;
import com.cavista.imagesearchingapp.R;
import com.cavista.imagesearchingapp.Utils.Constants;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class ImageSearchActivity extends AppCompatActivity implements ImagesAdapter.ImagesAdapterListener {

    private static final String TAG = ImageSearchActivity.class.getSimpleName();

    private CompositeDisposable disposable = new CompositeDisposable();
    private PublishSubject<String> publishSubject = PublishSubject.create();
    private ImageISearchnterface apiService;
    private ImagesAdapter mAdapter;
    private List<ImageData> imagesList = new ArrayList<>();

    @BindView(R.id.input_search)
    EditText inputSearch;


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_search);
        unbinder = ButterKnife.bind(this);

        Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAdapter = new ImagesAdapter(this, imagesList, this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerView.setAdapter(mAdapter);



        apiService = ApiClient.getClient().create(ImageISearchnterface.class);

        DisposableObserver<SearchImages> observer = getSearchObserver();

        disposable.add(
                publishSubject
                        .debounce(250, TimeUnit.MILLISECONDS)
                        .distinctUntilChanged()
                        .switchMapSingle(new Function<String, Single<SearchImages>>() {
                            @Override
                            public Single<SearchImages> apply(String s) throws Exception {
                                return apiService.searchImage(Constants.AUTH_TOKEN, s)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread());
                            }
                        })
                        .subscribeWith(observer));


        // skipInitialValue() - skip for the first time when EditText empty
        disposable.add(
                RxTextView.textChangeEvents(inputSearch)
                        .skipInitialValue()
                        .debounce(250, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(searchContactsTextWatcher()));

        disposable.add(observer);

        // passing empty string fetches all the contacts
        publishSubject.onNext("");
    }

    private DisposableObserver<SearchImages> getSearchObserver() {
        return new DisposableObserver<SearchImages>() {
            @Override
            public void onNext(SearchImages image) {
                imagesList.clear();
                if (image.getImageData().size() > 0) {
List<ImageData> list=image.getImageData();
                  imagesList.addAll(list);

                  /*  for (int j = 0; j < imageData.size(); j++) {
                        List<ImageJsonObject> imageJsonObjects = imageData.get(j).getImageJsonObject();
                        if(imageJsonObjects!=null)
                            imagesList.add(imageJsonObjects.get(0).getLink());
                    }*/
                }


                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
    }

    private DisposableObserver<TextViewTextChangeEvent> searchContactsTextWatcher() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                Log.d(TAG, "Search query: " + textViewTextChangeEvent.text());

                publishSubject.onNext(textViewTextChangeEvent.text().toString());
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Override
    protected void onDestroy() {
        disposable.clear();
        unbinder.unbind();
        super.onDestroy();
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onImageSelected(SearchImages contact) {

    }
}