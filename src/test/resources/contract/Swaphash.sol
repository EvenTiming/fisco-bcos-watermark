pragma solidity  ^0.4.24;
contract Swaphash{
    string im_hash_value;
    string wm_hash_value;
    string im_back_hashvalue;


    bool sendflag=true;
    bool backflag=false;
    bool send_get_flag=false;
    bool back_get_flag=false;

    function Clientsend(string memory wm_hashvalue,string memory im_hashvalue) public {
        if(sendflag){
            wm_hash_value=wm_hashvalue;
            im_hash_value=im_hashvalue;
            sendflag=false;
            send_get_flag=true;
        }
    }

    function Serversend(string memory backhashvalue) public {
        if(backflag){
            im_back_hashvalue=backhashvalue;
            backflag=false;
            back_get_flag=true;
        }
    }

    function serverget() public view returns(string memory,string memory){
        if(send_get_flag){
            backflag=true;
            send_get_flag=false;
            return(im_hash_value,wm_hash_value) ;
        }
        else
            return("please wait","");
    }

    function clientget() public view returns(string memory){
        if(back_get_flag){
            sendflag=true;
            back_get_flag=false;
            return im_back_hashvalue;
        }
        else
            return "please wait!";
    }

    function server_watch_client() public view returns(bool){
        return send_get_flag;
    }
    function client_watch_server() public view returns(bool){
        return back_get_flag;
    }

}