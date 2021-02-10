package com.fab.wallet.user.service;

import com.fab.wallet.Reply;
import com.fab.wallet.user.dto.UserProfileDto;
import com.fab.wallet.user.entities.UserTbl;
import com.fab.wallet.user.repo.UserRepo;
import com.fab.wallet.util.Const;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public boolean addUpdateUser(UserProfileDto userProfileDto,boolean isSignUp){
        Optional<UserTbl> optionalUserTbl=userRepo.findById(userProfileDto.getUserId());
        LocalDateTime now=LocalDateTime.now();
        UserTbl userTbl;
        if((isSignUp && optionalUserTbl.isPresent())||(!isSignUp && !optionalUserTbl.isPresent())){
            return false;
        }
        if(optionalUserTbl.isPresent()){
            userTbl=optionalUserTbl.get();
            userTbl.setLastUpdatedDateTime(now);
        }else{
            if(userProfileDto.getPassword()==null || userProfileDto.getPassword().isEmpty())
                return false;
            userTbl=new UserTbl();
            userTbl.setCreationDateTime(now);
            userTbl.setUserId(userProfileDto.getUserId());
        }
        userTbl.setPassword(userProfileDto.getPassword());
        userTbl.setFirstName(userProfileDto.getFirstName());
        userTbl.setLastName(userProfileDto.getLastName());
        userTbl.setDOB(userProfileDto.getDOB());
        userTbl.setExpiryDate(userProfileDto.getExpiryDate());
        userTbl.setAddress(userProfileDto.getAddress());
        userTbl.setCity(userProfileDto.getCity());
        userTbl.setState(userProfileDto.getState());
        userTbl.setCountry(userProfileDto.getCountry());
        userTbl.setPhone(userProfileDto.getPhone());
        userRepo.save(userTbl);
        return true;
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void loginUser(UserProfileDto userProfileDto, Reply reply){
        Optional<UserTbl> optionalUserTbl=userRepo.findById(userProfileDto.getUserId());
        if(!optionalUserTbl.isPresent()){
            reply.setMessage("INVALID USER ID", Const.MessageType.ERROR);
            return;
        }
        UserTbl userTbl=optionalUserTbl.get();
        if(!userTbl.getPassword().equals(userProfileDto.getPassword())){
            reply.setMessage("INVALID PASSWORD", Const.MessageType.ERROR);
            return;
        }
        reply.setMessage("LOGIN SUCCESSFULL", Const.MessageType.INFO);
        userProfileDto.setPassword("");
        copyFromTblToDTO(userProfileDto,userTbl);
        reply.setData(userProfileDto);
    }

    public UserProfileDto getUserProfile(String id){
        Optional<UserTbl> optionalUserTbl=userRepo.findById(id);
        if(!optionalUserTbl.isPresent())
            return null;
        UserProfileDto userProfileDto=new UserProfileDto();
        copyFromTblToDTO(userProfileDto,optionalUserTbl.get());
        return userProfileDto;
    }

        private void copyFromTblToDTO(UserProfileDto userProfileDto,UserTbl userTbl){
        userProfileDto.setUserId(userTbl.getUserId());
        userProfileDto.setFirstName(userTbl.getFirstName());
        userProfileDto.setLastName(userTbl.getLastName());
        userProfileDto.setDOB(userTbl.getDOB());
        userProfileDto.setExpiryDate(userTbl.getExpiryDate());
        userProfileDto.setAddress(userTbl.getAddress());
        userProfileDto.setCity(userTbl.getCity());
        userProfileDto.setState(userTbl.getState());
        userProfileDto.setCountry(userTbl.getCountry());
        userProfileDto.setPhone(userTbl.getPhone());
        userProfileDto.setWalletBalance(userTbl.getWalletBalance());
    }

}
