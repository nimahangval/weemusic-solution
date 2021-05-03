package com.weemusic.android.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.weemusic.android.R
import com.weemusic.android.core.DaggerAppComponent
import com.weemusic.android.core.DaggerDomainComponent
import com.weemusic.android.core.DaggerNetworkComponent
import com.weemusic.android.domain.AlbumsAdapter
import com.weemusic.android.domain.GetTopAlbumsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.new_activity_main.*
import java.util.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var getTopAlbumsUseCase: GetTopAlbumsUseCase
    private lateinit var adapter: AlbumsAdapter
    private lateinit var topAlbumsDisposable: Disposable
    private var count: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_activity_main)

        val networkComponent = DaggerNetworkComponent.create()
        val domainComponent = DaggerDomainComponent
            .builder()
            .networkComponent(networkComponent)
            .build()

        DaggerAppComponent
            .builder()
            .domainComponent(domainComponent)
            .build()
            .inject(this)

        displayTopAlbums()


        btnSort.setOnClickListener {
            count++

            if (count % 2 == 0) {
                displayTopAlbums()
                btnSort.text = "Sort: A - Z"
            } else {
                sortAlbums()
                btnSort.text = "Top: Featured"
            }
        }
    }

    override fun onResume() {
        super.onResume()
        displayTopAlbums()
        count = 0
    }

    private fun displayTopAlbums() {
        topAlbumsDisposable = getTopAlbumsUseCase
            .perform()
            .map { response ->
                response.topAlbums.albums
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer {
                adapter = AlbumsAdapter(it)
                recyclerViewFeed.adapter = adapter
                recyclerViewFeed.layoutManager = GridLayoutManager(this, 2)
            })
    }

    private fun sortAlbums() {
        topAlbumsDisposable = getTopAlbumsUseCase
            .perform()
            .map { response ->
                response.topAlbums.albums
            }
            .map {
                Collections.sort(it,
                    Comparator { lhs, rhs -> lhs.name.compareTo(rhs.name) })

                it
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer {
                adapter = AlbumsAdapter(it)
                recyclerViewFeed.adapter = adapter
                recyclerViewFeed.layoutManager = GridLayoutManager(this, 2)
            })
    }
}


