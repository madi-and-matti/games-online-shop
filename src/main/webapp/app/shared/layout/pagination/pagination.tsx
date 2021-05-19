import React from "react";
import {JhiItemCount} from "react-jhipster";
import { Row } from 'reactstrap';
import {JhiPagination} from "app/shared/layout/pagination/jhi-pagination";


const Pagination = ({pagination, handlePagination, items, totalItems }) => (
  <>
    {
      totalItems ? (
        <div className={items && items.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={pagination.activePage} total={totalItems} itemsPerPage={pagination.itemsPerPage}
                          i18nEnabled/>
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={pagination.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={pagination.itemsPerPage}
              totalItems={totalItems}
            />
          </Row>
        </div>
      ) : ('')
    }
  </>
);

export default Pagination;
