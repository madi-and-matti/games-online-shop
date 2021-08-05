import DOMPurify from 'dompurify';

const sanitize = DOMPurify.sanitize;

export const cleanText = (text: string) => {
  return sanitize(text);
};

export const formatCurrency = (num: number) => {
  if (num === 0) {
    return 'FREE';
  } else {
    const numStr = num.toString();
    return `$${numStr.substr(0, numStr.length - 2)}.${numStr.substr(numStr.length - 2, numStr.length)}`;
  }
};

export const formatPlayers = (minimumPlayers: number, maximumPlayers: number) => {
  if (maximumPlayers !== null && minimumPlayers !== null && maximumPlayers === minimumPlayers) {
    return `${maximumPlayers}`;
  } else if (maximumPlayers === null && minimumPlayers !== null) {
    return `${minimumPlayers}+`;
  } else if (maximumPlayers !== null && minimumPlayers === null) {
    return `up to ${maximumPlayers}`;
  } else if (maximumPlayers === null && minimumPlayers === null) {
    return '--';
  } else if (maximumPlayers !== null && minimumPlayers !== null) {
    return `${minimumPlayers}-${maximumPlayers}`;
  }
};

export const formatAge = (age: number) => {
  if (age === null) {
    return '--';
  } else {
    return `${age}+`;
  }
};

export const formatRating = (userRating: number) => {
  if (userRating === null) {
    return '--';
  } else if (userRating.toString().length === 1) {
    return `${userRating}.00`;
  } else if (userRating.toString().length <= 4) {
    return `${userRating}`;
  } else {
    return `${userRating.toString().substr(0, 4)}`;
  }
};

export const formatYearPublished = (yearPublished: number) => {
  if (yearPublished === null) {
    return '--';
  } else {
    return `${yearPublished}`;
  }
};
