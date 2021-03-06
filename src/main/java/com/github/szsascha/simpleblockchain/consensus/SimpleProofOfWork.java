package com.github.szsascha.simpleblockchain.consensus;

import com.github.szsascha.simpleblockchain.entities.Block;
import com.github.szsascha.simpleblockchain.entities.Transaction;
import com.github.szsascha.simpleblockchain.entities.TransactionBuilder;
import com.github.szsascha.simpleblockchain.util.HashUtils;
import com.github.szsascha.simpleblockchain.wallet.Wallet;

class SimpleProofOfWork implements Consensus {

    private long difficulty;

    private long reward;

    private Wallet rewardWallet;

    public SimpleProofOfWork(Wallet rewardWallet) {
        this.difficulty = 3;
        this.reward = 1;
        this.rewardWallet = rewardWallet;
    }

    @Override
    public void onBlockCreation(Block block) {
        String prefix = "";
        for (int i = 0; i < difficulty; i++) {
            prefix += "0";
        }

        String hash = "";
        long nonce = -1;
        while(!hash.startsWith(prefix)) {
            nonce++;
            hash = HashUtils.stringToSha256String(block.getHash() + nonce);
        }
    }

    @Override
    public Transaction createRewardTransaction() {
        return new TransactionBuilder()
                .fromAddress("")
                .toAddress(this.rewardWallet.getPublicKey())
                .amount(this.reward)
                .buildSimpleTransaction();
    }

}
