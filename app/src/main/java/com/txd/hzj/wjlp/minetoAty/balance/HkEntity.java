package com.txd.hzj.wjlp.minetoAty.balance;

/**
 * by Txunda_LH on 2018/2/27.
 */

public class HkEntity {
    String id,bank_num,open_bank,bank_name;

    public HkEntity(String id, String bank_num, String open_bank, String bank_name) {
        this.id = id;
        this.bank_num = bank_num;
        this.open_bank = open_bank;
        this.bank_name = bank_name;
    }

    public String getId() {
        return id;
    }

    public String getBank_num() {
        return bank_num;
    }

    public String getOpen_bank() {
        return open_bank;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBank_num(String bank_num) {
        this.bank_num = bank_num;
    }

    public void setOpen_bank(String open_bank) {
        this.open_bank = open_bank;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    @Override
    public String toString() {
        return "HkEntity{" +
                "id='" + id + '\'' +
                ", bank_num='" + bank_num + '\'' +
                ", open_bank='" + open_bank + '\'' +
                ", bank_name='" + bank_name + '\'' +
                '}';
    }
}
