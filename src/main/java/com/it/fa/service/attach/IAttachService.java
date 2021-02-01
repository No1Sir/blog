package com.it.fa.service.attach;

import com.github.pagehelper.PageInfo;
import com.it.fa.model.Attach;

public interface IAttachService {
    int add(Attach attach);

    PageInfo<Attach> findAll(int page, int limit);
}
