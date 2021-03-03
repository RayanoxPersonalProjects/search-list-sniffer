# search-list-sniffer [![Build Status](https://travis-ci.org/RayanoxPersonalProjects/search-list-sniffer.svg?branch=main)](https://travis-ci.org/RayanoxPersonalProjects/search-list-sniffer)

A bot which periodicly requests a list of web sites search lists to check when a product in newly available (ex: new music concert places, out of stock products, etc.)

# Usage

The project is usable simply by configuating the application.properties file. Whenever you want to scrap a new search list webpage, **the only thing you have to do** is to set up a new conf of a search list page in the **application.properties** file.

Among all the configuration properties (check the example 'application.properties' file in this repo), the main feature properties are the following ones (example):

```
conf.pagesConf[0].url=https://www.materiel.net/produit/201902140083.html
conf.pagesConf[0].cssSelector=div#js-modal-trigger__availability span.o-availability__value--stock_1

conf.pagesConf[1].url=https://www.ldlc.com/fiche/PB00265834.html
conf.pagesConf[1].cssSelector=div.modal-stock-web.stock-1
```

It is a list of configuration for each site you want to scrap (each searchlist page). It is composed of two elements:
- **url**: the URL of the searchlist webpage
- **cssSelector**: a **CSS selector of the element that must be null UNTIL the product is newly available** (then you will be notified by e-mail). Do your best effort to be sure that this condition is correctly respected, otherwise it the program won't work as expected for the main feature -> be notified when a product is NOT OUT OF STOCK anymore. **THIS PROPERTY IS ALMOST THE MOST IMPORTANT**.

# Contribution

Don't thank me, but you can contribute if you like coding and this repo. I'm not against evolutions.

# Last word

Be the cheater who will buy every rare product first ! Business is yours !
