# Place API DB Contract

The backend place APIs expect one unified table named `place_master`.

Required columns:

| Column | Meaning |
| --- | --- |
| `PLACE_ID` | Unique id, e.g. `PLACE00000001` |
| `PLACE_NM` | Place name |
| `PLACE_TY` | One of `cafe`, `library`, `store`, `stationery`, `print` |
| `LAT` | Latitude |
| `LNT` | Longitude |
| `ADDR` | Address |
| `TEL_NO` | Phone number |
| `WIFI_ST` | Wi-Fi status |
| `OTL_ST` | Outlet status |
| `NOI_LVL` | Noise level |
| `SEAT_TY` | Seat type |
| `DESC_TXT` | Short description |

API endpoints using this table:

- `GET /api/places`
- `GET /api/places?type=cafe`
- `GET /api/places?type=library`
- `GET /api/places?type=store`
- `GET /api/places?type=stationery`
- `GET /api/places?type=print`
- `GET /api/places/{placeId}`
- `GET /api/places/{placeId}/open-status`
- `POST /api/places`
- `PATCH /api/places/{placeId}`
- `DELETE /api/places/{placeId}`
- `GET /api/recommendations/places`

## Cafe one-line reviews

Cafe reviews use a separate table named `cafe_review`.

Required columns:

| Column | Meaning |
| --- | --- |
| `REVIEW_ID` | Unique id, e.g. `REV000000001` |
| `PLACE_ID` | Cafe place id from `place_master` |
| `USER_ID` | Reviewer user id from `user_master` |
| `REVIEW_TXT` | Original one-line review text |
| `CLEAN_TXT` | CleanBot-filtered review text |
| `SENTIMENT_TY` | `POSITIVE` or `NEGATIVE` |
| `CLEAN_FLG` | `Y` if no blocked word was detected, otherwise `N` |
| `REG_DT` | Review date |

Review APIs:

- `GET /api/places/{placeId}/reviews`
- `POST /api/places/{placeId}/reviews`

Reviews are allowed only when `place_master.PLACE_TY = 'cafe'`.

## Owner cafe editing

Owner cafe editing uses a mapping table named `place_owner`.

Required columns:

| Column | Meaning |
| --- | --- |
| `USER_ID` | Owner user id from `user_master` |
| `PLACE_ID` | Cafe place id from `place_master` |

Owner role:

- `user_master.ROLE_TY = 'O'` means cafe owner.
- `ROLE_TY = 'A'` can also access owner endpoints for admin support.

Owner APIs:

- `GET /api/owner/cafes`
- `PATCH /api/owner/cafes/{placeId}`
- `GET /api/owner/cafes/{placeId}/open-status`
- `POST /api/owner/cafes/{placeId}/open-status`
- `PATCH /api/owner/cafes/{placeId}/open-status`
- `GET /api/owner/cafes/{placeId}/status`
- `POST /api/owner/cafes/{placeId}/status`
- `PATCH /api/owner/cafes/{placeId}/status`
- `GET /api/owner/cafes/{placeId}/profile`
- `POST /api/owner/cafes/{placeId}/profile`
- `PATCH /api/owner/cafes/{placeId}/profile`
- `DELETE /api/owner/cafes/{placeId}/profile`
- `GET /api/owner/cafes/{placeId}/photos`
- `POST /api/owner/cafes/{placeId}/photos`
- `PATCH /api/owner/cafes/{placeId}/photos/{photoId}`
- `DELETE /api/owner/cafes/{placeId}/photos/{photoId}`

Owner update APIs only update rows where `PLACE_TY = 'cafe'` and the user is mapped in `place_owner`.

## Owner realtime occupancy and congestion

Realtime occupancy uses a table named `cafe_occupancy_status`.

Required columns:

| Column | Meaning |
| --- | --- |
| `PLACE_ID` | Cafe place id from `place_master` |
| `CURRENT_CNT` | Current number of people |
| `CAPACITY_CNT` | Cafe capacity |
| `CONGESTION_TY` | `LOW`, `MEDIUM`, or `HIGH` |
| `UPDATED_AT` | Last update timestamp |

The backend can calculate `CONGESTION_TY` from `CURRENT_CNT / CAPACITY_CNT` when the request omits it.

## Owner cafe profile

Cafe profile uses a table named `cafe_profile`.

Required columns:

| Column | Meaning |
| --- | --- |
| `PLACE_ID` | Cafe place id from `place_master` |
| `INTRO_TXT` | Cafe introduction |
| `NOTICE_TXT` | Owner notice |
| `OPENING_HOURS` | Business hours text |
| `MENU_TXT` | Menu summary text |
| `SNS_URL` | SNS or external URL |
| `UPDATED_AT` | Last update timestamp |

## Owner cafe photos

Cafe photos use a table named `cafe_photo`.

Required columns:

| Column | Meaning |
| --- | --- |
| `PHOTO_ID` | Unique id, e.g. `PHOTO0000001` |
| `PLACE_ID` | Cafe place id from `place_master` |
| `PHOTO_URL` | Stored image URL/path |
| `DISPLAY_ORD` | Display order from 1 to 6 |
| `REG_DT` | Registration timestamp |

The backend limits each cafe to at most 6 photos.

## Cafe open status

Cafe open status uses a table named `cafe_open_status`.

Required columns:

| Column | Meaning |
| --- | --- |
| `PLACE_ID` | Cafe place id from `place_master` |
| `OPEN_FLG` | `Y` when open, `N` when closed |
| `STATUS_MSG` | Short status message, e.g. temporary closure reason |
| `UPDATED_AT` | Last update timestamp |

Open status APIs:

- `GET /api/places/{placeId}/open-status`
- `GET /api/owner/cafes/{placeId}/open-status`
- `POST /api/owner/cafes/{placeId}/open-status`
- `PATCH /api/owner/cafes/{placeId}/open-status`

Public open status lookup is allowed for cafes only.

## Owner verification

Owner verification uses a table named `owner_verification`.

Required columns:

| Column | Meaning |
| --- | --- |
| `VERIFICATION_ID` | Unique id, e.g. `OV0000000001` |
| `USER_ID` | Requesting user id from `user_master` |
| `PLACE_ID` | Cafe place id from `place_master` |
| `BUSINESS_NO` | Business registration number |
| `DOCUMENT_URL` | Uploaded verification document URL/path |
| `STATUS_TY` | `PENDING`, `APPROVED`, or `REJECTED` |
| `REJECT_REASON` | Reject reason when rejected |
| `REQ_DT` | Request timestamp |
| `REVIEW_DT` | Admin review timestamp |

Owner verification APIs:

- `GET /api/owner/verifications/me`
- `POST /api/owner/verifications`
- `GET /api/admin/owner-verifications`
- `GET /api/admin/owner-verifications?status=PENDING`
- `PATCH /api/admin/owner-verifications/{verificationId}`

When an admin approves a verification, the backend:

- inserts `(USER_ID, PLACE_ID)` into `place_owner` if absent
- updates `user_master.ROLE_TY` to `O`
