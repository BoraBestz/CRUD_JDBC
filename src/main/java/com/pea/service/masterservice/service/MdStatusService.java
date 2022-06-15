package com.pea.service.masterservice.service;

import com.pea.service.masterservice.dto.PageDto;
import com.pea.service.masterservice.dto.TblMdStatusPageDto;
import com.pea.service.masterservice.repository.TblMdStatusJdbcRepository;
import com.pea.service.masterservice.repository.TblMdStatusRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MdStatusService {
    private final ModelMapper modelMapper;
    private final TblMdStatusRepository tblMdStatusRepository;
    private final TblMdStatusJdbcRepository tblMdStatusJdbcRepository;

    @Autowired
    public MdStatusService(ModelMapper modelMapper,
                           TblMdStatusRepository tblMdStatusRepository,
                           TblMdStatusJdbcRepository tblMdStatusJdbcRepository) {
        this.modelMapper = modelMapper;
        this.tblMdStatusRepository = tblMdStatusRepository;
        this.tblMdStatusJdbcRepository = tblMdStatusJdbcRepository;
    }

    public Page<TblMdStatusPageDto> findAllPage(TblMdStatusPageDto param){
        List<TblMdStatusPageDto> result = tblMdStatusJdbcRepository.findAll(param);
        PageDto newPage = createPage(param.getPage(),param.getPerPage(),result.size());
        return new PageImpl<>(result.subList(newPage.getStart(), newPage.getEnd()), newPage.getPaging(), result.size());
    }

    public PageDto createPage(Integer page,Integer perPage,Integer size){
        PageDto newPage = new PageDto();
        Pageable paging = PageRequest.of(page-1, perPage);
        int start = Math.min((int)paging.getOffset(), size);
        int end = Math.min((start + paging.getPageSize()), size);
        newPage.setPaging(paging);
        newPage.setStart(start);
        newPage.setEnd(end);
        return newPage;
    }

}
