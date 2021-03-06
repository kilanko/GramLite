package ng.lekki.gramlite

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Base64
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.*
import android.widget.Toast
import com.breuhteam.apprate.AppRate
import com.kennyc.bottomsheet.BottomSheet
import com.kennyc.bottomsheet.BottomSheetListener
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.io.InputStream
import java.util.*

class MainActivity : AppCompatActivity(),SwipeRefreshLayout.OnRefreshListener,BottomSheetListener {


    var showing = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(applicationContext,android.R.color.white))

        loader.setOnRefreshListener(this )

        webView.settings.javaScriptEnabled = true
        webView.settings?.allowUniversalAccessFromFileURLs = true
        webView?.settings?.domStorageEnabled = true
        webView?.settings?.cacheMode = WebSettings.LOAD_NO_CACHE
        val jsInterface = JavaScriptInterface()
        webView?.addJavascriptInterface(jsInterface, "JSInterface")

        AppRate.app_launched(this,getPackageName(),0,4);
        webView?.webChromeClient = object: WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)

            }


        }

        speedDial.inflate(R.menu.sheet)

        speedDial.setOnActionSelectedListener(object :SpeedDialView.OnActionSelectedListener{
            override fun onActionSelected(item: SpeedDialActionItem?): Boolean {

                when(item!!.id){

                    R.id.home ->{

                        webView.loadUrl("https://www.instagram.com/")

                    }

                    R.id.trends ->{


                        webView.loadUrl("https://www.instagram.com/explore/tags/trending/?hl=en")
                    }


                    R.id.downloads ->{

                        startActivity(Intent(this@MainActivity,Downloads::class.java))

                    }


                }



                return false
            }


        })




        webView?.webViewClient = object: WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)


                val arriveString = String.format("javascript:var Arrive=function(e,t,n){function r(e,t,n){l.addMethod(t,n,e.unbindEvent),l.addMethod(t,n,e.unbindEventWithSelectorOrCallback),l.addMethod(t,n,e.unbindEventWithSelectorAndCallback)}function i(e){e.arrive=f.bindEvent,r(f,e,'unbindArrive'),e.leave=d.bindEvent,r(d,e,'unbindLeave')}if(e.MutationObserver&&'undefined'!=typeof HTMLElement){var o=0,l=function(){var t=HTMLElement.prototype.matches||HTMLElement.prototype.webkitMatchesSelector||HTMLElement.prototype.mozMatchesSelector||HTMLElement.prototype.msMatchesSelector;return{matchesSelector:function(e,n){return e instanceof HTMLElement&&t.call(e,n)},addMethod:function(e,t,r){var i=e[t];e[t]=function(){return r.length==arguments.length?r.apply(this,arguments):'function'==typeof i?i.apply(this,arguments):n}},callCallbacks:function(e,t){t&&t.options.onceOnly&&1==t.firedElems.length&&(e=[e[0]]);for(var n,r=0;n=e[r];r++)n&&n.callback&&n.callback.call(n.elem,n.elem);t&&t.options.onceOnly&&1==t.firedElems.length&&t.me.unbindEventWithSelectorAndCallback.call(t.target,t.selector,t.callback)},checkChildNodesRecursively:function(e,t,n,r){for(var i,o=0;i=e[o];o++)n(i,t,r)&&r.push({callback:t.callback,elem:i}),i.childNodes.length>0&&l.checkChildNodesRecursively(i.childNodes,t,n,r)},mergeArrays:function(e,t){var n,r={};for(n in e)e.hasOwnProperty(n)&&(r[n]=e[n]);for(n in t)t.hasOwnProperty(n)&&(r[n]=t[n]);return r},toElementsArray:function(t){return n===t||'number'==typeof t.length&&t!==e||(t=[t]),t}}}(),c=function(){var e=function(){this._eventsBucket=[],this._beforeAdding=null,this._beforeRemoving=null};return e.prototype.addEvent=function(e,t,n,r){var i={target:e,selector:t,options:n,callback:r,firedElems:[]};return this._beforeAdding&&this._beforeAdding(i),this._eventsBucket.push(i),i},e.prototype.removeEvent=function(e){for(var t,n=this._eventsBucket.length-1;t=this._eventsBucket[n];n--)if(e(t)){this._beforeRemoving&&this._beforeRemoving(t);var r=this._eventsBucket.splice(n,1);r&&r.length&&(r[0].callback=null)}},e.prototype.beforeAdding=function(e){this._beforeAdding=e},e.prototype.beforeRemoving=function(e){this._beforeRemoving=e},e}(),a=function(t,r){var i=new c,o=this,a={fireOnAttributesModification:!1};return i.beforeAdding(function(n){var i,l=n.target;(l===e.document||l===e)&&(l=document.getElementsByTagName('html')[0]),i=new MutationObserver(function(e){r.call(this,e,n)});var c=t(n.options);i.observe(l,c),n.observer=i,n.me=o}),i.beforeRemoving(function(e){e.observer.disconnect()}),this.bindEvent=function(e,t,n){t=l.mergeArrays(a,t);for(var r=l.toElementsArray(this),o=0;o<r.length;o++)i.addEvent(r[o],e,t,n)},this.unbindEvent=function(){var e=l.toElementsArray(this);i.removeEvent(function(t){for(var r=0;r<e.length;r++)if(this===n||t.target===e[r])return!0;return!1})},this.unbindEventWithSelectorOrCallback=function(e){var t,r=l.toElementsArray(this),o=e;t='function'==typeof e?function(e){for(var t=0;t<r.length;t++)if((this===n||e.target===r[t])&&e.callback===o)return!0;return!1}:function(t){for(var i=0;i<r.length;i++)if((this===n||t.target===r[i])&&t.selector===e)return!0;return!1},i.removeEvent(t)},this.unbindEventWithSelectorAndCallback=function(e,t){var r=l.toElementsArray(this);i.removeEvent(function(i){for(var o=0;o<r.length;o++)if((this===n||i.target===r[o])&&i.selector===e&&i.callback===t)return!0;return!1})},this},s=function(){function e(e){var t={attributes:!1,childList:!0,subtree:!0};return e.fireOnAttributesModification&&(t.attributes=!0),t}function t(e,t){e.forEach(function(e){var n=e.addedNodes,i=e.target,o=[];null!==n&&n.length>0?l.checkChildNodesRecursively(n,t,r,o):'attributes'===e.type&&r(i,t,o)&&o.push({callback:t.callback,elem:i}),l.callCallbacks(o,t)})}function r(e,t){return l.matchesSelector(e,t.selector)&&(e._id===n&&(e._id=o++),-1==t.firedElems.indexOf(e._id))?(t.firedElems.push(e._id),!0):!1}var i={fireOnAttributesModification:!1,onceOnly:!1,existing:!1};f=new a(e,t);var c=f.bindEvent;return f.bindEvent=function(e,t,r){n===r?(r=t,t=i):t=l.mergeArrays(i,t);var o=l.toElementsArray(this);if(t.existing){for(var a=[],s=0;s<o.length;s++)for(var u=o[s].querySelectorAll(e),f=0;f<u.length;f++)a.push({callback:r,elem:u[f]});if(t.onceOnly&&a.length)return r.call(a[0].elem,a[0].elem);setTimeout(l.callCallbacks,1,a)}c.call(this,e,t,r)},f},u=function(){function e(){var e={childList:!0,subtree:!0};return e}function t(e,t){e.forEach(function(e){var n=e.removedNodes,i=[];null!==n&&n.length>0&&l.checkChildNodesRecursively(n,t,r,i),l.callCallbacks(i,t)})}function r(e,t){return l.matchesSelector(e,t.selector)}var i={};d=new a(e,t);var o=d.bindEvent;return d.bindEvent=function(e,t,r){n===r?(r=t,t=i):t=l.mergeArrays(i,t),o.call(this,e,t,r)},d},f=new s,d=new u;t&&i(t.fn),i(HTMLElement.prototype),i(NodeList.prototype),i(HTMLCollection.prototype),i(HTMLDocument.prototype),i(Window.prototype);var h={};return r(f,h,'unbindAllArrive'),r(d,h,'unbindAllLeave'),h}}(window,'undefined'==typeof jQuery?null:jQuery,void 0);")
                val photoString = String.format("javascript:var options = { fireOnAttributesModification: true, onceOnly: false, existing: true};  document.arrive('article',options,function() { if( window.location.href.indexOf('www.instagram.com/p/') != -1 ||  window.location.href.indexOf('www.instagram.com/v/') != -1 || window.location.href.indexOf('www.instagram.com/') != -1){ if(this.getElementsByClassName('downloadBtn').length !=0){}else{ var button = document.createElement('button'); button.setAttribute('type','button'); button.setAttribute('id','save');button.setAttribute('class','downloadBtn');button.innerHTML = 'Download';var links = this.getElementsByClassName('c-Yi7')[0].getAttribute('href');button.dataset.src=links;button.dataset.type='photo';button.addEventListener('click', function(e) { e.preventDefault();window.JSInterface.saveItem(links,'photo');});this.getElementsByClassName('eLAPa')[0].appendChild(button);}}});")
                val videoString = String.format("javascript:var options = { fireOnAttributesModification: true, onceOnly: false, existing: true};  document.arrive('article',options,function() { if( window.location.href.indexOf('www.instagram.com/p/') != -1 ||  window.location.href.indexOf('www.instagram.com/v/') != -1 || window.location.href.indexOf('www.instagram.com/') != -1){ if(this.getElementsByClassName('downloadBtn').length !=0){}else{ var button = document.createElement('button'); button.setAttribute('type','button'); button.setAttribute('id','save');button.setAttribute('class','downloadBtn');button.innerHTML = 'Download';var links = this.getElementsByClassName('c-Yi7')[0].getAttribute('href');button.dataset.src=links;button.dataset.type='video';button.addEventListener('click', function(e) { e.preventDefault();window.JSInterface.saveItem(links,'photo');});this.getElementsByClassName('kPFhm')[0].appendChild(button);}}});")
                val galleryString = String.format("javascript:var options = { fireOnAttributesModification: true, onceOnly: false, existing: true};  document.arrive('article',options,function() { if( window.location.href.indexOf('www.instagram.com/p/') != -1 ||  window.location.href.indexOf('www.instagram.com/v/') != -1 || window.location.href.indexOf('www.instagram.com/') != -1){ if(this.getElementsByClassName('downloadBtn').length !=0){}else{ var button = document.createElement('button'); button.setAttribute('type','button'); button.setAttribute('id','save');button.setAttribute('class','downloadBtn');button.innerHTML = 'Download';var links = this.getElementsByClassName('c-Yi7')[0].getAttribute('href');button.dataset.src=links;button.dataset.type='video';button.addEventListener('click', function(e) { e.preventDefault();window.JSInterface.saveItem(links,'photo');});this.getElementsByClassName('kM-aa')[0].appendChild(button);}}});")


                injectCSS("style.css")
                webView.evaluateJavascript(arriveString,null)
                webView.evaluateJavascript(photoString,null)
                webView.evaluateJavascript(videoString,null)
                webView.evaluateJavascript(galleryString,null)

                loader.isRefreshing = false


            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)


                loader.isRefreshing = true

            }
        }




        webView?.setOnKeyListener(object: View.OnKeyListener{
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {

                if (event!!.getAction()!= KeyEvent.ACTION_DOWN)
                    return true;

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (webView!!.canGoBack()) {
                        webView!!.goBack()
                    } else {


                    }
                    return true
                }
                return false

            }


        })



        webView?.loadUrl("https://www.instagram.com/instagram/feed/?hl=en")


    }


    fun loadMenu(){

        BottomSheet.Builder(this@MainActivity)
                .setSheet(R.menu.sheet)
                .setTitle(R.string.menu_title)
                .setListener(this)
                .show();
    }


    override fun onRefresh() {
        webView?.loadUrl("https://www.instagram.com")


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         menuInflater.inflate(R.menu.tools,menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item!!.itemId){

            R.id.saves ->{

                startActivity(Intent(this@MainActivity,Downloads::class.java))

            }


            R.id.heart ->{

                AppRate.app_launched(this@MainActivity,getPackageName());


            }
        }

        return super.onOptionsItemSelected(item)
    }


    inner class JavaScriptInterface() {

        @JavascriptInterface
        fun saveItem(videoURL: String,type:String) {


        val intint = Intent(this@MainActivity,Whiskey::class.java)
            intint.putExtra(Intent.EXTRA_TEXT,videoURL)
            startActivity(intint)





        }


    }




    fun injectCSS( filespaces: String) {

        try {
            val inputStream = assets?.open(filespaces)
            val buffer = ByteArray(inputStream!!.available())
            inputStream.read(buffer)
            inputStream.close()
            val encoded = Base64.encodeToString(buffer, Base64.NO_WRAP)
            webView!!.loadUrl("javascript:(function() {" +
                    "var parent = document.getElementsByTagName('head').item(0);" +
                    "var style = document.createElement('style');" +
                    "style.type = 'text/css';" +
                    // Tell the browser to BASE64-decode the string into your script !!!
                    "style.innerHTML = decodeURIComponent(escape(window.atob('" + encoded + "')));" +
                    "parent.appendChild(style)" +
                    "})()")
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    override fun onSheetItemSelected(p0: BottomSheet, item: MenuItem?, p2: Any?) {


    }

    override fun onSheetDismissed(p0: BottomSheet, p1: Any?, p2: Int) {


    }

    override fun onSheetShown(p0: BottomSheet, p1: Any?) {


    }




}
