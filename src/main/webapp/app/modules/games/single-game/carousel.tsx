import '../games.scss';

import React from 'react';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCalendar, faSmile, faTrophy, faUsers} from "@fortawesome/free-solid-svg-icons";
import {formatAge, formatPlayers, formatRating, formatYearPublished} from "app/shared/util/render-utils";
import {Row, Col} from 'reactstrap';

export const Carousel = ({minimumPlayers, maximumPlayers, minimumAge, yearPublished, userRating}) => {
  return (
    <Row className="carousel-div offset-1 col-10 mt-5 d-flex justify-around align-content-center">
      <Col className="d-flex flex-column align-items-center bold-text">
        <p>Number of Players</p>
        <FontAwesomeIcon icon={faUsers}/>
        <p className="mt-3">{formatPlayers(minimumPlayers, maximumPlayers)}</p>
      </Col>
      <Col className="d-flex flex-column align-items-center bold-text">
        <p>Age Range</p>
        <FontAwesomeIcon icon={faSmile}/>
        <p className="mt-3">{formatAge(minimumAge)}</p>
      </Col>
      <Col className="d-flex flex-column align-items-center bold-text">
        <p>Year Published</p>
        <FontAwesomeIcon icon={faCalendar}/>
        <p className="mt-3">{formatYearPublished(yearPublished)}</p>
      </Col>
      <Col className="d-flex flex-column align-items-center bold-text">
        <p>User Rating</p>
        <FontAwesomeIcon icon={faTrophy}/>
        <p className="mt-3">{formatRating(userRating)}</p>
      </Col>
    </Row>
  )
}
