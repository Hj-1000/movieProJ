package com.hj1000.movieinfo.Controller;

import com.hj1000.movieinfo.DTO.MovieDTO;
import com.hj1000.movieinfo.Service.MovieService;
import com.hj1000.movieinfo.Util.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.lang.model.element.Name;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Log4j2
//swagger로 클래스 이름을 정의
@Tag(name = "MovieController", description = "영화정보")
public class MovieController {
    private final MovieService movieService;
    private final PaginationUtil paginationUtil;

    @Operation(summary = "등록폼", description = "등록폼 페이지로 이동한다.")
    @GetMapping("/register")
    public String registerHTML(Model model){
        // 검증처리가 필요하면 빈 MovieDTO를 생성해서 전달한다.
        // 입력폼에도 무조건 Object = Feild로 구성한다
        model.addAttribute("movieDTO", new MovieDTO());
        return "register";
     }
    @Operation(summary = "등록창", description = "데이터베이스에 등록후 목록으로 이동")
    @PostMapping("/register")
    public String registerService(MovieDTO movieDTO,@RequestParam("imageFile") MultipartFile imageFile){
        movieService.insert(movieDTO, imageFile);

        return "redirect:/list";
    }

    @Operation(summary = "수정폼", description = "해당 데이터 조회후 수정폼 페이지로 이동한다.")
    @GetMapping("/modify")
    public String modifyHTML(Integer code, Model model){
        MovieDTO movieDTO =
                movieService.read(code);
        model.addAttribute("movieDTO",movieDTO);

        return "modify";
    }

    @Operation(summary = "등록창", description = "수정할 내용을 데이터베이스에 저장후 목록으로 이동한다.")
    @PostMapping("/modify")
    public String modifyService(MovieDTO movieDTO, MultipartFile imageFile){
        movieService.update(movieDTO, imageFile);

        return "redirect:/list";
    }

    @Operation(summary = "삭제처리", description = "해당번호를 삭제후 목록으로 이동한다.")
    @GetMapping("/remove")
    public String removeServiceHTML(Integer code){

        movieService.delete(code);

        return "redirect:/list";
    }


    @Operation(summary = "전체조회", description = "전체목록을 조회한다.")
    @GetMapping({"/","/index","/list"})
    public String listServiceHTML(@PageableDefault(page=1) Pageable page, Model model){
        Page<MovieDTO> movieDTOS =
            movieService.list(page);
        Map<String, Integer> pageInfo = paginationUtil.pagination(movieDTOS);
        model.addAttribute("movieDTOS",movieDTOS);
        model.addAllAttributes(pageInfo);

        return "list";
    }

    @Operation(summary = "개별조회", description = "해당번호의 데이터를 조회한다.")
    @GetMapping("/read")
    public String readServiceHTML(Integer code, Model model){
        MovieDTO movieDTO = movieService.read(code);
        model.addAttribute("movieDTO",movieDTO);
        return "read";
    }

}
