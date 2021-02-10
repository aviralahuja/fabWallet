package com.fab.wallet.util;

import java.util.HashMap;
import java.util.Map;

public class Const {
    public enum MessageType{
        ERROR((short)0),INFO((short)1);
        MessageType(short key) {
            this.key = key;
        }
        short key;
        public short getKey() {
            return key;
        }
    }
    public enum SessionEnum{
        LOGIN_ID("ID");
        SessionEnum(String key) {
            this.key = key;
        }
        String key;
        public String getKey() {
            return key;
        }
    }
    public enum PaymentMode{
        UPI("UPI",(short)0),BANK_ACCOUNT("Bank Account",(short)1),PATYM("patym",(short)2);
        public static Map<Short,PaymentMode> map=new HashMap();
        static {
            for(PaymentMode pm:PaymentMode.values())
                map.put(pm.getFEConst(),pm);
        }
        PaymentMode(String key,short FEConst) {
            this.key = key;
            this.FEConst=FEConst;
        }
        String key;
        short FEConst;
        public String getKey() {
            return key;
        }

        public short getFEConst() {
            return FEConst;
        }

        public void setFEConst(short FEConst) {
            this.FEConst = FEConst;
        }
    }
    public enum TransactionType{
        WALLET_TO_WALLET("Wallet To Wallet",(short)0),ADD_MONEY("Add Money",(short)1);
        public static Map<Short,TransactionType> map=new HashMap();
        static {
            for(TransactionType tt:TransactionType.values())
                map.put(tt.getFEConst(),tt);
        }
        TransactionType(String key,short FEConst) {
            this.key = key;
            this.FEConst=FEConst;
        }
        String key;
        short FEConst;
        public String getKey() {
            return key;
        }
        public short getFEConst() {
            return FEConst;
        }

        public void setFEConst(short FEConst) {
            this.FEConst = FEConst;
        }
    }
}
