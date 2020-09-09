package com.alchemy.gateway.exchangeclients.bitfinex;

import com.alchemy.gateway.core.common.CoinPair;
import com.alchemy.gateway.core.common.CoinPairSymbolConverter;

import java.util.Arrays;
import java.util.List;



/**
 * @author Administrator
 */
public class BitfinexCoinPairSymbolConverter implements CoinPairSymbolConverter {
    private static final List<String> SUPPORTED_BUYER_COINS = Arrays.asList("storj", "dcr", "wan", "ont", "dat", "datx", "akro", "musk", "gnt", "pai", "pvt", "adx", "ae", "kcash", "iost", "ftt", "seele", "hpt", "zjlt", "ekt", "lxt", "egcc", "dac", "bft", "xvg", "nuls", "tt", "eos", "fair", "node", "bhd", "chat", "nexo", "one", "idt", "utk", "cova", "salt", "dot", "lol", "pc", "mx", "ckb", "trx", "lba", "mco", "qun", "ssp", "cvnt", "iris", "bch", "eko", "cro", "ada", "theta", "ugas", "ardr", "btm", "wtc", "rvn", "npxs", "cnns", "for", "ruff", "dac", "rte", "iost", "wtc", "mtx", "xzc", "mana", "xmr", "soc", "rdn", "vidy", "xrp", "bifi", "xlm", "sbtc", "gt", "kcash", "snc", "blz", "ogo", "lxt", "egt", "dock", "ada", "req", "trio", "algo", "ogn", "mtn", "pai", "hot", "evx", "iris", "qtum", "bht", "skm", "smt", "cdc", "em", "xrp", "neo", "zil", "rccc", "abt", "powr", "ksm", "omg", "tnt", "fsn", "ht", "man", "ltc", "bsv", "qsp", "cmt", "powr", "fsn", "hive", "uuu", "ocn", "atp", "btt", "xem", "mxc", "gxc", "top", "eos", "mex", "etc", "iota", "swftc", "vsys", "lym", "cvcoin", "ren", "let", "cnn", "gsc", "yee", "bch", "ven", "dta", "etc", "ocn", "gas", "box", "doge", "smt", "phx", "bhd", "bts", "eos", "ht", "knc", "iost", "get", "18c", "atom", "rcn", "bkbt", "bsv", "tnb", "lsk", "ltc", "knc", "rcn", "zrx", "waves", "meet", "ven", "btc", "hive", "get", "nkn", "em", "box", "egt", "hit", "mtl", "new", "skm", "rdn", "tos", "meet", "cre", "zla", "stk", "fair", "xrp", "nuls", "ht", "ekt", "xtz", "arpa", "gas", "hit", "mx", "vidy", "tnb", "ncc", "atp", "top", "gsc", "nkn", "lamb", "iic", "zec", "mds", "rvn", "pay", "link", "cnn", "uuu", "cro", "egcc", "pax", "for", "portal", "topc", "wicc", "ycc", "soc", "dgd", "vet", "gxc", "ftt", "usdc", "trx", "mds", "iic", "ost", "algo", "etc", "poly", "ctxc", "eth", "ctxc", "lsk", "cvc", "srn", "eos", "pay", "dash", "uuu", "node", "vet", "datx", "wxt", "wan", "zla", "topc", "lol", "ycc", "but", "one", "dcr", "uip", "but", "algo", "dcr", "ncc", "hc", "elf", "btg", "elf", "dgd", "stk", "omg", "cvc", "trio", "dot", "snc", "iris", "vsys", "lym", "srn", "ada", "dash", "mex", "link", "tt", "hot", "nuls", "btm", "gnx", "ost", "ekt", "kan", "nas", "portal", "bix", "evx", "pvt", "waxp", "propy", "seele", "ltc", "dac", "soc", "eng", "qtum", "eko", "theta", "poly", "atp", "mtn", "xzc", "etc", "mtx", "utk", "ssp", "xmr", "idt", "bht", "hb10", "ardr", "ae", "nexo", "pnt", "bft", "edu", "tos", "storj", "sc", "gt", "ae", "ont", "zrx", "cre", "pai", "skm", "musk", "mt", "arpa", "adx", "egt", "bhd", "wtc", "atom", "nas", "waves", "em", "waxp", "etn", "iota", "link", "cnns", "swftc", "gnt", "mx", "uip", "cvcoin", "ckb", "new", "ksm", "tnb", "eth", "ont", "btm", "rvn", "zil", "xvg", "dta", "rbtc", "xtz", "gve", "wxt", "vidy", "bkbt", "rsr", "lba", "pc", "lun", "tnt", "18c", "grs", "nano", "hc", "req", "kcash", "mds", "ela", "qsp", "bix", "elf", "act", "ftt", "ogn", "akro", "hpt", "ht", "hc", "wicc", "vet", "yee", "iost", "ast", "xlm", "rte", "bt2", "lamb", "rsr", "rccc", "ren", "zec", "for", "ast", "eos", "abt", "gxc", "act", "one", "man", "lol", "steem", "snt", "ocn", "btt", "cvc", "fti", "aac", "blz", "bat", "ltc", "zen", "grs", "usdt", "pvt", "icx", "dock", "mt", "cdc", "xtz", "steem", "uip", "eth", "tusd", "etn", "itc", "lamb", "doge", "lun", "she", "bts", "tt", "ctxc", "kmd", "dta", "waves", "mana", "ogo", "let", "appc", "dash", "cre", "bat", "wicc", "aidoc", "dbc", "omg", "vsys", "aac", "kmd", "bch", "ckb", "itc", "xzc", "smt", "xmr", "bcd", "gt", "xrp", "cro", "qtum", "she", "gve", "aidoc", "node", "zen", "loom", "ugas", "trx", "qun", "aac", "cvnt", "ogo", "loom", "cnns", "bat", "btc", "bch", "mt", "atom", "arpa", "neo", "xmx", "pnt", "zec", "chat", "doge", "zrx", "bix", "npxs", "new", "gnx", "lxt", "xmx", "bht", "bcv", "ela", "dock", "dbc", "dat", "qash", "theta", "bsv", "cmt", "act", "let", "icx", "bt1", "hit", "zjlt", "top", "fti", "ada", "gnt", "hive", "iota", "edu", "sc", "kan", "appc", "steem", "itc", "eng", "lamb", "rsr", "seele", "nkn", "bts", "cova", "nas", "bcx", "dash", "ven", "kcash", "ksm", "snt", "dgb", "salt", "ogn", "yee", "ruff", "mana", "akro", "kan", "mco", "ncash", "hpt", "xem", "wpr", "knc", "ren", "waxp", "xlm", "zil", "ncash", "btt", "fsn", "propy", "cmt", "mco", "lba", "ela", "dgb", "gtc", "nano", "wpr", "uc", "wxt", "uc", "nano", "btt", "ruff", "qash", "xlm", "gtc", "bcv");



    @Override
    public String coinPairToSymbol(CoinPair coinPair) {
        String buyCoin = coinPair.getBuyCoin();
        String sellCoin = coinPair.getSellCoin();
        if (BiffinexFeatures.USDT.equalsIgnoreCase(buyCoin))
        {
            buyCoin=BiffinexFeatures.USD;
        }
        return BiffinexFeatures.PREFIX +sellCoin+buyCoin;
    }

    @Override
    public CoinPair symbolToCoinPair(String symbol) {

        //去掉 前面的 t
        if (symbol.startsWith(BiffinexFeatures.PREFIX))
        {
            symbol=symbol.substring(1);
        }

        for (String buyerCoin : SUPPORTED_BUYER_COINS) {

                String sellerCoin = symbol.substring(buyerCoin.length());
                return new CoinPair(sellerCoin, buyerCoin);

        }
        return null;
    }
}
