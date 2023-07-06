package uk.gigbookingapp.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import uk.gigbookingapp.backend.entity.User;

import java.util.List;

@Service
public interface UserMapper extends BaseMapper<User> {}
