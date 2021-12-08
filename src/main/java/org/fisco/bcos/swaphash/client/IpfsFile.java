package org.fisco.bcos.swaphash.client;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;

import java.io.*;

public class IpfsFile {
    private static  IPFS ipfs;
    public IpfsFile(IPFS ipfs){
        this.ipfs=ipfs;
    }
    public String upload(String filePathName) throws IOException {
        //filePathName指的是文件的上传路径+文件名，如D:/1.png
        NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(new File(filePathName));
        MerkleNode addResult = ipfs.add(file).get(0);
        return addResult.hash.toString();
    }

    public void download(String filePathName,String hash) throws IOException {
        Multihash filePointer = Multihash.fromBase58(hash);
        byte[] data = ipfs.cat(filePointer);
        if(data != null){
            File file  = new File(filePathName);
            if(file.exists()){
                file.delete();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data,0,data.length);
            fos.flush();
            fos.close();
        }
    }


}


