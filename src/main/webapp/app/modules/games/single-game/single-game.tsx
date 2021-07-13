import '../games.scss';

import {RouteComponentProps} from "react-router-dom";
import {connect} from "react-redux";
import {IRootState} from "app/shared/reducers";
import {getSingleGame} from "app/modules/games/games.reducer";
import React, {useEffect, useState} from "react";
import {Col, Container, Row} from 'reactstrap';
import {cleanText, formatCurrency} from "app/shared/util/render-utils";
import {CartQuantityBox} from "app/modules/games/single-game/cart-quantity-box";
import {CategoriesMechanics} from "app/modules/games/single-game/categories-mechanics";
import {Carousel} from "app/modules/games/single-game/carousel";
import PageNotFound from "app/shared/error/page-not-found";

export interface ISingleGameProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const SingleGame = (props: ISingleGameProps) => {
  const [desiredQuantity, setDesiredQuantity] = useState(1);
  const [notFound, setNotFound] = useState(false);

  useEffect(() => {
    props.getSingleGame(props.match.params.id);
  }, []);
  const {
    name, price, quantity, rulesUrl, largeImageUrl, description, categories, mechanics,
    yearPublished, minimumAge, minimumPlayers, maximumPlayers, userRating
  } = props.singleGame;

  useEffect(() => {
    if (props.errorMessage) {
      setNotFound(true);
    } else {
      setNotFound(false);
    }
  }, [props.errorMessage]);

  // eslint-disable-next-line no-console
  return (
    <div>
      {!notFound && name && (
        <Container className="body-div mt-5" fluid={true}>
          <Row className="info-div d-flex justify-content-between">
            <Col className="image-div offset-1" lg="4" sm="6">
              <img src={largeImageUrl}
                   alt="game image"
                   className="single-game-img"
              />
            </Col>
            <Col className="content-div col-6">
              <Row className="title-and-cart-button-div mb-4 d-flex">
                <Col className="name-and-price-div col-5">
                  <h1>{name}</h1>
                  <p className="mt-3 bold-text">{formatCurrency(price)}</p>
                </Col>
                <CartQuantityBox checkoutDisabled={quantity === 0}
                                 quantity={quantity}
                                 desiredQuantity={desiredQuantity}
                                 setDesiredQuantity={setDesiredQuantity}
                />
              </Row>
              <div className="description col-9 ml-0 pl-0 new-line"
                   dangerouslySetInnerHTML={{__html: cleanText(description.text)}}/>
              <CategoriesMechanics categories={categories} mechanics={mechanics}/>
              <Row className="mt-4">
                {rulesUrl.length > 0 && (
                  <a href={rulesUrl} className="link">Official Rules</a>
                )}
              </Row>
            </Col>
          </Row>
          <Carousel
            maximumPlayers={maximumPlayers}
            minimumPlayers={minimumPlayers}
            minimumAge={minimumAge}
            userRating={userRating}
            yearPublished={yearPublished}/>
        </Container>
      )} {notFound && (
      <PageNotFound/>
    )}
    </div>
  )
}

const mapStateToProps = (storeState: IRootState) => ({
  singleGame: storeState.games.singleGame,
  errorMessage: storeState.games.errorMessage,
});

const mapDispatchToProps = {getSingleGame};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SingleGame);
