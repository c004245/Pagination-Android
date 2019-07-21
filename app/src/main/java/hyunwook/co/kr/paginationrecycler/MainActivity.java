package hyunwook.co.kr.paginationrecycler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import hyunwook.co.kr.paginationrecycler.adapter.PostRecyclerAdapter;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.recyclerView)
    RecyclerView mRecycler;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private PostRecyclerAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    public static final int PAGE_START = 1;

    private int itemCount = 0;
    private int currentPage = PAGE_START;
    private int totalPage = 10;

    private boolean isLastPage = false;
    private boolean isLoading = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        swipeRefreshLayout.setOnRefreshListener(this);

        mRecycler.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mLayoutManager);

        mAdapter = new PostRecyclerAdapter(new ArrayList<PostItem>());
        mRecycler.setAdapter(mAdapter);

        prepareListItem();

        mRecycler.addOnScrollListener(new PaginationScrollListener(mLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                prepareListItem();
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

    }

    private void prepareListItem() {
        final ArrayList<PostItem> items = new ArrayList<>();
        new Handler().postDelayed(() -> {
            for (int i = 0; i < 10; i++) {
                itemCount++;
                PostItem postItem = new PostItem();
                postItem.setTitle("Test App --->" + itemCount);
                postItem.setDesc("Fake Android Apps With Over 50,000");
                items.add(postItem);
            }

            if (currentPage != PAGE_START) {
                mAdapter.removeLoading();
            }
            mAdapter.addAll(items);
            swipeRefreshLayout.setRefreshing(false);

            if (currentPage < totalPage) {
                mAdapter.addLoading();
            } else {
                isLastPage = true;
            }
            isLoading = false;
        }, 1500);
    }

    @Override
    public void onRefresh() {
        itemCount = 0;
        currentPage = PAGE_START;

        isLastPage = false;
        mAdapter.clear();
        prepareListItem();
    }
}
