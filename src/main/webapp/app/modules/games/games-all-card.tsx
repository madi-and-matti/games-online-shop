import './games.scss';

import React from 'react';
import StarRatings from 'react-star-ratings';
import { Card, CardBody, CardImg} from 'reactstrap';

const GamesAllCard = ({game}) => {
  const rating = (game.userRating ? game.userRating : 0);
  const price = (game.price ? game.price / 100 : 0).toFixed(2);
  return (
    <Card className="all-games">
      <CardImg top src={game.smallImageUrl} alt={`${game.name} image`} />
      <CardBody>
        <strong>{game.name}</strong>
        <p>${price}</p>
        <StarRatings
          rating={rating}
          starRatedColor="#7f52ff"
          numberOfStars={5}
          starDimension="1.25rem"
          starSpacing="0"
        />
      </CardBody>
    </Card>
  )
};

export default GamesAllCard;
