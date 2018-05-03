package com.txd.hzj.wjlp.minetoAty.balance;

/**
 * by Txunda_LH on 2018/2/27.
 */

public class PtEntity {
    String bank_card_id, bank_card_code, bank_name, open_bank, name;

    public PtEntity(String bank_card_id, String bank_card_code, String bank_name, String open_bank, String name) {
        this.bank_card_id = bank_card_id;
        this.bank_card_code = bank_card_code;
        this.bank_name = bank_name;
        this.open_bank = open_bank;
        this.name = name;
    }

    public void setBank_card_id(String bank_card_id) {
        this.bank_card_id = bank_card_id;
    }

    public void setBank_card_code(String bank_card_code) {
        this.bank_card_code = bank_card_code;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public void setOpen_bank(String open_bank) {
        this.open_bank = open_bank;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBank_card_id() {
        return bank_card_id;
    }

    public String getBank_card_code() {
        return bank_card_code;
    }

    public String getBank_name() {
        return bank_name;
    }

    public String getOpen_bank() {
        return open_bank;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "PtEntity{" +
                "bank_card_id='" + bank_card_id + '\'' +
                ", bank_card_code='" + bank_card_code + '\'' +
                ", bank_name='" + bank_name + '\'' +
                ", open_bank='" + open_bank + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
