import '../games.scss';

import React from 'react';
import {Row, Col} from 'reactstrap';

export const CategoriesMechanics = ({categories, mechanics}) => {
  return (
    <Row className="categories-and-mechanics-div d-flex justify-content-start mt-2">
      {
        categories.length > 0 && (
          <Col className="categories col-5">
            <div className="bold-text">Categories</div>
            <ul>
              {categories.map(item => {
                return (
                  <li key={item.id}>{item.name}</li>
                )
              })}
            </ul>
          </Col>
        )} {
      mechanics.length > 0 && (
        <Col className="mechanics">
          <div className="bold-text">Mechanics</div>
          <ul>
            {mechanics.map(item => {
              return (
                <li key={item.id}>{item.name}</li>
              )
            })}
          </ul>
        </Col>
      )}
    </Row>
  )
}
