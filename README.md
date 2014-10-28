<article class="markdown-body entry-content" itemprop="mainContentOfPage">
<h1><a name="user-content-phonegap-toast-plugin" class="anchor" href="#phonegap-toast-plugin" aria-hidden="true"><span class="octicon octicon-link"></span></a>PhoneGap GetLocation plugin</h1>

<p>for Android and iOS by jacky</p>

<h2>
<a name="user-content-0-index" class="anchor" href="#0-index" aria-hidden="true"><span class="octicon octicon-link"></span></a>0. Index</h2>

<ol class="task-list">
<li><a href="#1-description">Description</a></li>
<!--<li><a href="#2-screenshots">Screenshots</a></li> -->
<li>
<a href="#3-installation">Installation</a>

<ol class="task-list">
<li><a href="#automatically-cli--plugman">Automatically</a></li>
<li><a href="#manually">Manually</a></li>
<li><a href="#phonegap-build">PhoneGap Build</a></li>
</ol>
</li>
<li><a href="#4-usage">Usage</a></li>
</ol>

<h2>
<a name="user-content-1-description" class="anchor" href="#1-description" aria-hidden="true"><span class="octicon octicon-link"></span></a>1. Description</h2>

<p>This plugin allows you to get an address where you are by &quot;高德地圖API&quot; on iOS and Android.</p>


<h2>
<a name="user-content-3-installation" class="anchor" href="#3-installation" aria-hidden="true"><span class="octicon octicon-link"></span></a>2. Installation</h2>

<p>To use this plugin you will need to make sure you've created your account with <a href="http://lbs.amap.com/">"高德地圖API"</a> and have an <code>GAODE_KEY</code></p>

<h3>
<a name="user-content-automatically-cli--plugman" class="anchor" href="#automatically-cli--plugman" aria-hidden="true"><span class="octicon octicon-link"></span></a>Automatically</h3>

<p>This plugin requires Cordova CLI.

To install the plugin in your app, execute the following (replace variables where necessary)...</p>

<pre><code>$ cordova plugin add https://github.com/maomaohang2013/GetLocationPlugin.git --variable GAODE_KEY="123456789"
</code></pre>

<h3>
<a name="user-content-phonegap-build" class="anchor" href="#phonegap-build" aria-hidden="true"><span class="octicon octicon-link"></span></a>PhoneGap Build</h3>

<p>The plugin works with PhoneGap build too, but only with PhoneGap 3.0 and up.</p>


<div class="highlight highlight-xml"><pre><span class="nt">&lt;gap:plugin</span> <span class="na">name=</span><span class="s">"com.example.getlocation.GetLocationPlugin"</span> <span class="nt">/&gt;</span>
</pre></div>

<p>getlocation.js is brought in automatically. There is no need to change or add anything in your html.</p>

<h2>
<a name="user-content-4-usage" class="anchor" href="#4-usage" aria-hidden="true"><span class="octicon octicon-link"></span></a>3. Usage</h2>



<p>You can copy-paste these lines of code for a quick test:</p>
<div class="highlight highlight-html">
<pre>

<span class="nt">&lt;button</span> <span class="na">onclick=</span><span class="s">"window.plugins.GetLocationPlugin.getlocation("address",function(a){alert('address: '+ a)}, function(b){alert('error: ' + b}); "</span><span class="nt">&gt;</span>get address<span class="nt">&lt;/button&gt;</span>
<span class="nt">&lt;button</span> <span class="na">onclick=</span><span class="s">"window.plugins.GetLocationPlugin.getlocation("longitude",function(a){alert('longitude: '+ a)}, function(b){alert('error: ' + b}); "</span><span class="nt">&gt;</span>get longitude<span class="nt">&lt;/button&gt;</span>
<span class="nt">&lt;button</span> <span class="na">onclick=</span><span class="s">"window.plugins.GetLocationPlugin.getlocation("latitude",function(a){alert('longitude: '+ a)}, function(b){alert('error: ' + b}); "</span><span class="nt">&gt;</span>get latitude<span class="nt">&lt;/button&gt;</span>
</pre>
</div>




</article>
