package com.example.yunita.tradiogc;

import android.test.ActivityInstrumentationTestCase2;

public class TradeUseCaseTest extends ActivityInstrumentationTestCase2 {

    public TradeUseCaseTest() {
        super(com.example.yunita.tradiogc.MainActivity.class);
    }

    public void testOfferTradeWithFriends() {
        User borrower = new User(username, password);
        User owner = new User(username, password);
        Trade trade = new Trade(trade_id, borrower, owner);
        trade.addBorrowerItem(borrower_item_id);
        trade.addOwnerItem(owner_item_id);


        assertTrue(trade.getBorrowerItemList.contains(borrower_item_id));
        assertTrue(trade.getOwnerItemList.contains(owner_item_id));
    }

    public void testGetTradeNotification() {
        User borrower = new User(username, password);
        User owner = new User(username, password);
        Trade trade = new Trade(trade_id, borrower, owner);

        owner.addNodtfication(trade_id);
        assertTrue(owner.getNotificationList().contains(trade));
    }

    public void testAcceptTrade() {
        User borrower = new User(username, password);
        User owner = new User(username, password);
        Trade trade = new Trade(trade_id, borrower, owner);

        borrower.addCurrentTrade(trade);
        owner.addCurrentTrade(trade);

        assertTrue(borrower.getCurrentTradeList.contains(trade));
        assertTrue(owner.getCurrentTradeList.contains(trade));
    }

    public void testDeclineTrade() {
        User borrower = new User(username, password);
        User owner = new User(username, password);
        Trade trade = new Trade(trade_id, borrower, owner);

        owner.addNodification(trade_id);
        owner.removeNotification(trade_id);

        assertFalse(owner.getNotificationList().contains(trade));
        assertFalse(owner.getNotificationList().contains(trade));
    }

    public void testOfferCounterTrade() {
        User borrower = new User(username, password);
        User owner = new User(username, password);
        Trade counter_trade = new Trade(trade_id, borrower, owner);

        borrower.addNodtfication(trade_id);
        assertTrue(borrower.getCurrentTradeList.contains(counter_trade));
    }

    public void testEditTrade() {
        User borrower = new User(username, password);
        User owner = new User(username, password);
        Trade trade = new Trade(trade_id, borrower, owner);

        borrower.addCurrentTrade(trade);
        owner.addCurrentTrade(trade);

        borrower.getCurrentTrade(trade_id).addBorrowerItem(borrower_item_id1);
        borrower.getCurrentTrade(trade_id).removeBorrowerItem(borrower_item_id1);
        borrower.getCurrentTrade(trade_id).addBorrowerItem(borrower_item_id2);

        assertFalse(borrower.getCurrentTrade(trade_id).getBorrowerItemList.contains(borrower_item_id1));
        assertTrue(borrower.getCurrentTrade(trade_id).getBorrowerItemList.contains(borrower_item_id2));
        assertFalse(owner.getCurrentTrade(trade_id).getBorrowerItemList.contains(borrower_item_id1));
        assertTrue(owner.getCurrentTrade(trade_id).getBorrowerItemList.contains(borrower_item_id2));
    }

    public void testDeleteTrade() {
        User borrower = new User(username, password);
        User owner = new User(username, password);
        Trade trade = new Trade(trade_id, borrower, owner);
        borrower.addCurrentTrade(trade);
        owner.addCurrentTrade(trade);

        borrower.removeCurrentTrade(trade_id);
        owner.removeCurrentTrade(trade_id);

        assertFalse(borrower.getCurrentTradeList().contains(trade));
        assertFalse(owner.getCurrentTradeList().contains(trade));
    }

    public void testEmailTradeInfo() {
        User borrower = new User(username, password);
        User owner = new User(username, password);
        Trade trade = new Trade(trade_id, borrower, owner);
        owner.addCurrentTrade(trade);
        owner.getCurrentTrade(trade_id).commentOnTrade("comment");

        assertTrue(owner.getCurrentTrade(trade_id).getComment.equals("comment"));
    }

    public void testBrowsePastTrades() {
        User borrower = new User(username, password);
        User owner = new User(username, password);
        Trade trade = new Trade(trade_id, borrower, owner);
        owner.addPastTrade(trade);

        assertTrue(owner.getPastTradeList().contains(trade));
    }

    public void testBrowseCurrentTrades() {
        User borrower = new User(username, password);
        User owner = new User(username, password);
        Trade trade = new Trade(trade_id, borrower, owner);
        owner.addCurrentTrade(trade);

        assertTrue(owner.getCurrentTradeList().contains(trade));
    }

    public void testBrowseMySentTrades() {
        User borrower = new User(username, password);
        User owner = new User(username, password);
        Trade trade = new Trade(trade_id, borrower, owner);
        owner.addMySentTrade(trade);

        assertTrue(owner.getMySentTradeList().contains(trade));
    }

    public void testBrowseTradesOfferedToMe() {
        User borrower = new User(username, password);
        User owner = new User(username, password);
        Trade trade = new Trade(trade_id, borrower, owner);
        owner.addTradesOfferedToMe(trade);

        assertTrue(owner.getTradesOfferedToMeList().contains(trade));
    }

}
