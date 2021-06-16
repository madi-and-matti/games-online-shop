import {RouteComponentProps} from "react-router-dom";
import {connect} from "react-redux";
import {AllGames} from "app/modules/games/all-games";
import {IRootState} from "app/shared/reducers";
import {ACTION_TYPES, getSingleGame} from "app/modules/games/games.reducer";
import React, {useEffect, useState} from "react";

export interface ISingleGameProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const SingleGame = (props: ISingleGameProps) => {
  useEffect(() => {
    props.getSingleGame(props.match.params.id);
  }, []);
  const {name, price, quantity, rulesUrl, largeImageUrl, description} = props.singleGame;
  // eslint-disable-next-line no-console
  return (
    <div className="body-div">
      <div className="info-div">
        {largeImageUrl && (
          <div className="image-div">
            <img src={largeImageUrl}
              alt="game image"
            />
          </div>
        )
        }
        <div className="content-div">
          <div className="title-and-cart-button-div">
            <div className="name-and-price-div">
              <h1>{name}</h1>
              <p>{price}</p>
            </div>
            <div className="cart-button-div">
              <button>Add to Cart</button>
              <p>Quantity: {quantity}</p>
            </div>
          </div>
          <div className="description">
            {description.text}
          </div>
          <div className="categories-and-mechanics-div">
            <div className="categories"></div>
            <div className="mechanics"></div>
          </div>
          {
            rulesUrl && <a href={rulesUrl}>Official Rules</a>
          }
        </div>
      </div>
      <div className="carousel-div"></div>
    </div>
  )
}

const mapStateToProps = (storeState: IRootState) => ({
  singleGame: storeState.games.singleGame,
});

const mapDispatchToProps = {getSingleGame};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SingleGame);
