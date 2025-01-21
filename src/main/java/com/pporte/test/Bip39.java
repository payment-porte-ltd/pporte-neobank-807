package com.pporte.test;

import org.stellar.sdk.KeyPair;
import com.soneso.stellarmnemonics.Wallet;
import com.soneso.stellarmnemonics.derivation.Ed25519Derivation;
import com.soneso.stellarmnemonics.mnemonic.Mnemonic;

public class Bip39 {
	
	public static void main(String[] args) throws Exception {
		//char[] mnemonic = generateMnemonic();
		//generateKeyPairs(mnemonic);
		//generateKeyPairsWithPassPhrase(mnemonic);
		
		char [] mnemonic = "sound rough galaxy solid subway lock input adult across atom awesome deal".toCharArray();
		masterKeyGeneration(mnemonic);
//		masterKeyGeneration(mnemonic);
	}
	
	public static char[] generateMnemonic() {
		char[] mnemonic = null;
		try {
			mnemonic = Wallet.generate12WordMnemonic();
			String[] words = String.valueOf(mnemonic).split(" ");
			
			StringBuffer sb = new StringBuffer();
		      for(int i = 0; i < words.length; i++) {
		    	  if(i==words.length-1) {
		    		  sb.append(words[i]);
		    	  }else {
		    		  sb.append(words[i]+",");
		    	  }
		         
		      }
		      String str = sb.toString();
			System.out.println("str "+str);
			
		} catch (Exception e) {
			System.out.println("Error is "+e.getMessage());
		}
		return mnemonic;
	}
	/*Without passphrase*/
	public static void generateKeyPairs(char[] mnemonic) {
		try {			
			//System.out.println("mnemonic is "+String.valueOf(mnemonic));
			KeyPair keyPair0 = Wallet.createKeyPair(mnemonic, null, 0);
			//KeyPair keyPair1 = Wallet.createKeyPair(mnemonic, null, 1);
			System.out.println("Public key is "+keyPair0.getAccountId());
			System.out.println("Private key is "+String.valueOf(keyPair0.getSecretSeed()) );
			
		} catch (Exception e) {
			System.out.println("Error from generateKeyPairs is "+e.getMessage());
		}
	}
	
	/*With passphrase*/
	
	public static void generateKeyPairsWithPassPhrase(char[] mnemonic) {
		try {			
			char[] passphrase = "p4ssphr4se".toCharArray();
			KeyPair keyPair0 = Wallet.createKeyPair(mnemonic, passphrase, 0);
			System.out.println("Public1 key is "+keyPair0.getAccountId());
			System.out.println("Private2 key is "+String.valueOf(keyPair0.getSecretSeed()) );
			
		} catch (Exception e) {
			System.out.println("Error from generateKeyPairs is "+e.getMessage());
		}
	}
	
	
	public static void masterKeyGeneration(char[] mnemonic) {
		System.out.println("");
		try {		
			long start = System.currentTimeMillis();
			// some time passes
			
			byte[] bip39Seed = Mnemonic.createSeed(mnemonic, null);
			System.out.println("seed is "+new String(bip39Seed));
			Ed25519Derivation masterPrivateKey = Ed25519Derivation.fromSecretSeed(bip39Seed);
			Ed25519Derivation purpose = masterPrivateKey.derived(44);
			Ed25519Derivation coinType = purpose.derived(148);

			Ed25519Derivation account0 = coinType.derived(0);
			KeyPair keyPair0 = KeyPair.fromSecretSeed(account0.getPrivateKey());
			System.out.println("Public key is "+keyPair0.getAccountId());
			System.out.println("Private key is "+String.valueOf(keyPair0.getSecretSeed()));
			/*
			Ed25519Derivation account4 = coinType.derived(4);
			KeyPair keyPair4 = KeyPair.fromSecretSeed(account4.getPrivateKey());
			System.out.println("Public key is "+keyPair4.getAccountId());
			System.out.println("Private key is "+String.valueOf(keyPair4.getSecretSeed()));
			*/
			long end = System.currentTimeMillis();
			long elapsedTime = end - start;
			System.out.println("elapsedTime "+elapsedTime);
		} catch (Exception e) {
			System.out.println("Error is masterKeyGeneration "+e.getMessage());
		}
	}

}
